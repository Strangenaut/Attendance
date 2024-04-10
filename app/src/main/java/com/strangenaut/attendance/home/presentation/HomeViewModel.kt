package com.strangenaut.attendance.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.home.domain.repository.AttendanceRepository
import com.strangenaut.attendance.home.domain.util.TokenGenerator
import com.strangenaut.attendance.home.model.Credentials
import com.strangenaut.attendance.home.model.Lesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var updateCredentials = false

    init {
        onEvent(HomeEvent.GetUser)
        onEvent(HomeEvent.GetDisciplines)
        onEvent(HomeEvent.GetCurrentLesson)
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetUser -> {
                val email = auth.currentUser?.email ?: ""

                viewModelScope.launch {
                    val getUserResponse = attendanceRepository.getUser(email)
                    var user = User()

                    when (getUserResponse) {
                        is Success -> user = getUserResponse.data as User
                        is Failure -> {
                            _error.postValue(getUserResponse.message)
                        }
                    }
                    _state.update {
                        it.copy(currentUser = user)
                    }
                }
            }
            is HomeEvent.GetDisciplines -> {
                viewModelScope.launch {
                    val email = auth.currentUser?.email ?: ""
                    val getDisciplinesResponse = attendanceRepository.getDisciplines(email)
                    var disciplines: List<String> = listOf()

                    when (getDisciplinesResponse) {
                        is Success -> {
                            val list = getDisciplinesResponse.data as List<*>
                            val disciplinesString = mutableListOf<String>()

                            for (discipline in list) {
                                disciplinesString += discipline.toString()
                            }
                            disciplines = disciplinesString
                        }
                        is Failure -> {
                            _error.postValue(getDisciplinesResponse.message)
                        }
                    }
                    _state.update {
                        it.copy(disciplines = disciplines)
                    }
                }
            }
            is HomeEvent.GetCurrentLesson -> {
                viewModelScope.launch {
                    val email = auth.currentUser?.email ?: ""
                    val getCurrentLessonResponse = attendanceRepository.getCurrentLesson(email)

                    when (getCurrentLessonResponse) {
                        is Success -> {
                            if (getCurrentLessonResponse.data is Lesson) {
                                _state.update {
                                    it.copy(currentLesson = getCurrentLessonResponse.data)
                                }
                                startCredentialsUpdating()
                            }
                        }
                        is Failure -> _error.postValue(getCurrentLessonResponse.message)
                    }
                }
            }
            is HomeEvent.AddDiscipline -> {
                viewModelScope.launch {
                    val disciplines = _state.value.disciplines

                    attendanceRepository.addDiscipline(
                        _state.value.currentUser,
                        event.discipline
                    )
                    _state.update {
                        it.copy(disciplines = disciplines + event.discipline)
                    }
                }
            }
            is HomeEvent.StartLesson -> {
                viewModelScope.launch {
                    val dateTime = LocalDateTime.now()

                    val day = dateTime.dayOfMonth.convertToStandard()
                    val month = dateTime.monthValue.convertToStandard()
                    val year = dateTime.year
                    val date = "${day}.${month}.${year}"

                    val hours = dateTime.hour.convertToStandard()
                    val minutes = dateTime.minute.convertToStandard()
                    val seconds = dateTime.second.convertToStandard()
                    val nano = dateTime.nano.convertToStandard()
                    val time = "$hours:$minutes"

                    val host = _state.value.currentUser.email.replace('.', '_')
                    val id = "$date;$hours:$minutes:$seconds:$nano".replace('.', '_')
                    val initialToken = TokenGenerator.generateToken()

                    val credentials = Credentials(
                        host = host,
                        id = id,
                        token = initialToken
                    )

                    val lesson = Lesson(
                        discipline = event.discipline,
                        date = date,
                        time = time,
                        credentials = credentials,
                        host = _state.value.currentUser,
                    )

                    attendanceRepository.setCurrentLesson(lesson)
                    _state.update {
                        it.copy(currentLesson = lesson)
                    }
                    startCredentialsUpdating()
                }
            }
            is HomeEvent.StopLesson -> {
                updateCredentials = false

                viewModelScope.launch {
                    val email = auth.currentUser?.email ?: ""
                    val stopLessonResponse = attendanceRepository.removeCurrentLesson(email)
                    val lesson = _state.value.currentLesson ?: return@launch
                    val saveHostedLessonResponse = attendanceRepository.saveHostedLesson(lesson)

                    when (stopLessonResponse) {
                        is Success -> {
                            _state.update {
                                it.copy(currentLesson = null)
                            }
                        }
                        is Failure -> _error.postValue(stopLessonResponse.message)
                    }

                    if (saveHostedLessonResponse is Failure) {
                        _error.postValue(saveHostedLessonResponse.message)
                    }
                }
            }
        }
    }

    private fun startCredentialsUpdating() {
        viewModelScope.launch(Dispatchers.Default) {
            val currentId = _state.value.currentLesson?.credentials?.id
            updateCredentials = true

            while (updateCredentials) {
                delay(10000)

                if (_state.value.currentLesson?.credentials?.id != currentId) {
                    return@launch
                }

                if (_state.value.currentLesson == null) {
                    continue
                }

                val token = TokenGenerator.generateToken()
                val credentials = Credentials(
                    host = _state.value.currentLesson!!.credentials.host,
                    id = _state.value.currentLesson!!.credentials.id,
                    token = token
                )

                val lesson = _state.value.currentLesson!!.copy(
                    credentials = credentials
                )

                attendanceRepository.setCurrentLesson(lesson)
                _state.update {
                    it.copy(currentLesson = lesson)
                }
            }
        }
    }

    private fun Int.convertToStandard(): String {
        return "${if (this < 10) "0" else ""}$this"
    }
}