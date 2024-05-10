package com.strangenaut.attendance.home.di

import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.core.domain.repository.UserRepository
import com.strangenaut.attendance.home.domain.usecase.AddDiscipline
import com.strangenaut.attendance.home.domain.usecase.GetCurrentLesson
import com.strangenaut.attendance.home.domain.usecase.GetDisciplines
import com.strangenaut.attendance.home.domain.usecase.GetUser
import com.strangenaut.attendance.home.domain.usecase.HomeUseCases
import com.strangenaut.attendance.home.domain.usecase.JoinLesson
import com.strangenaut.attendance.home.domain.usecase.RemoveDiscipline
import com.strangenaut.attendance.home.domain.usecase.StartCurrentLessonListening
import com.strangenaut.attendance.home.domain.usecase.StartLesson
import com.strangenaut.attendance.home.domain.usecase.StopCurrentLessonListening
import com.strangenaut.attendance.home.domain.usecase.StopLesson
import com.strangenaut.attendance.home.domain.usecase.UpdateCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeUseCases(
        userRepository: UserRepository,
        lessonRepository: LessonRepository
    ): HomeUseCases  {
        return HomeUseCases(
            getUser = GetUser(userRepository),
            getDisciplines = GetDisciplines(userRepository),
            addDiscipline = AddDiscipline(userRepository),
            removeDiscipline = RemoveDiscipline(userRepository),
            getCurrentLesson = GetCurrentLesson(lessonRepository),
            startLesson = StartLesson(lessonRepository),
            startCurrentLessonListening = StartCurrentLessonListening(lessonRepository),
            updateCredentials = UpdateCredentials(lessonRepository),
            stopCurrentLessonListening = StopCurrentLessonListening(lessonRepository),
            stopLesson = StopLesson(lessonRepository),
            joinLesson = JoinLesson(lessonRepository)
        )
    }
}