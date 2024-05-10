package com.strangenaut.attendance.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.core.data.repository.LessonRepositoryImpl
import com.strangenaut.attendance.core.data.repository.UserRepositoryImpl
import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.core.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideDatabase(): FirebaseDatabase {
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideLessonRepository(
        app: AttendanceApp,
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): LessonRepository {
        return LessonRepositoryImpl(app = app, auth = auth, database = database)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        app: AttendanceApp,
        auth: FirebaseAuth,
        store: FirebaseFirestore
    ): UserRepository {
        return UserRepositoryImpl(app = app, auth = auth, store = store)
    }
}