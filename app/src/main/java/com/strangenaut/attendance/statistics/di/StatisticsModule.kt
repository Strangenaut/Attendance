package com.strangenaut.attendance.statistics.di

import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.statistics.domain.usecases.GetPastLessons
import com.strangenaut.attendance.statistics.domain.usecases.StartPastLessonsListening
import com.strangenaut.attendance.statistics.domain.usecases.StatisticsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Provides
    @Singleton
    fun provideStatisticsUseCases(
        lessonRepository: LessonRepository
    ): StatisticsUseCases {
        return StatisticsUseCases(
            getPastLessons = GetPastLessons(lessonRepository),
            startPastLessonsListening = StartPastLessonsListening(lessonRepository)
        )
    }
}