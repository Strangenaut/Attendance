package com.strangenaut.attendance.statistics.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.attendance.statistics.domain.usecases.StatisticsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsUseCases: StatisticsUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(StatisticsState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun initializeState() {
        _state.update {
            StatisticsState()
        }
        onEvent(StatisticsEvent.GetPastLessons)
        onEvent(StatisticsEvent.StartPastLessonsListening)
    }

    private fun onEvent(event: StatisticsEvent) {
        viewModelScope.launch {
            try {
                executeEvent(event)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private suspend fun executeEvent(event: StatisticsEvent) {
        when(event) {
            is StatisticsEvent.GetPastLessons -> {
                val lessons = statisticsUseCases.getPastLessons()

                _state.update {
                    it.copy(lessons = lessons)
                }
            }
            is StatisticsEvent.StartPastLessonsListening -> {
                statisticsUseCases.startPastLessonsListening(
                    onDataChange = {
                        onEvent(StatisticsEvent.GetPastLessons)
                    },
                    onCancelled = { errorMessage ->
                        _error.postValue(errorMessage)
                    }
                )
            }
        }
    }
}