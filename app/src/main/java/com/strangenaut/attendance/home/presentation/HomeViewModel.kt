package com.strangenaut.attendance.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.home.domain.usecase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var updateCredentials = false

    fun initializeState() {
        _state.update {
            HomeState()
        }
        onEvent(HomeEvent.GetUser)
        onEvent(HomeEvent.GetDisciplines)
        onEvent(HomeEvent.GetCurrentLesson)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            try {
                executeEvent(event)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private suspend fun executeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUser -> {
                val user = homeUseCases.getUser()
                _state.update {
                    it.copy(currentUser = user)
                }
            }
            is HomeEvent.GetDisciplines -> {
                val disciplines = homeUseCases.getDisciplines()
                _state.update {
                    it.copy(disciplines = disciplines)
                }
            }
            is HomeEvent.GetCurrentLesson -> {
                val emptyLesson = Lesson()
                val lesson = homeUseCases.getCurrentLesson()

                if (lesson != emptyLesson) {
                    _state.update {
                        it.copy(currentLesson = lesson)
                    }
                    startCurrentLessonListening()
                    startCredentialsUpdating()
                }
            }
            is HomeEvent.AddDiscipline -> {
                val disciplines = _state.value.disciplines

                homeUseCases.addDiscipline(event.discipline)
                _state.update {
                    it.copy(disciplines = disciplines + event.discipline)
                }
            }
            is HomeEvent.RemoveDiscipline -> {
                val disciplines = _state.value.disciplines

                homeUseCases.removeDiscipline(event.discipline)
                _state.update {
                    it.copy(disciplines = disciplines - event.discipline)
                }
            }
            is HomeEvent.StartLesson -> {
                val user = _state.value.currentUser
                val lesson = homeUseCases.startLesson(user, event.discipline)
                _state.update {
                    it.copy(currentLesson = lesson)
                }
                startCurrentLessonListening()
                startCredentialsUpdating()
            }
            is HomeEvent.StopLesson -> {
                updateCredentials = false
                homeUseCases.stopCurrentLessonListening()
                val lesson = _state.value.currentLesson ?: return
                homeUseCases.stopLesson(lesson)
                _state.update {
                    it.copy(currentLesson = null)
                }
            }
            is HomeEvent.JoinLesson -> {
                homeUseCases.joinLesson(_state.value.currentUser, event.credentials)
            }
        }
    }

    private fun startCurrentLessonListening() {
        homeUseCases.startCurrentLessonListening(
            onDataChange = { currentLesson ->
                _state.update {
                    it.copy(currentLesson = currentLesson)
                }
            },
            onCancelled = { errorMessage ->
                _error.postValue(errorMessage)
            }
        )
    }

    private fun startCredentialsUpdating() {
        viewModelScope.launch(Dispatchers.Default) {
            val currentId = _state.value.currentLesson?.credentials?.id
            updateCredentials = true

            while (updateCredentials) {
                delay(2000)
                val currentLesson = _state.value.currentLesson

                if (currentLesson?.credentials?.id != currentId) {
                    return@launch
                }

                val lessonWithoutCredentials = currentLesson?.copy(
                    credentials = Credentials(
                        host = currentLesson.credentials.host,
                        id = currentLesson.credentials.id,
                        token = ""
                    )
                )
                _state.update {
                    it.copy(currentLesson = lessonWithoutCredentials)
                }
                delay(1000)

                if (_state.value.currentLesson == null) {
                    return@launch
                }

                val lesson = homeUseCases
                    .updateCredentials(currentLesson = _state.value.currentLesson!!)
                _state.update {
                    it.copy(currentLesson = lesson)
                }
            }
        }
    }
}