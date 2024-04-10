package com.strangenaut.attendance.home.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.strangenaut.attendance.home.data.repository.AttendanceRepositoryImpl
import com.strangenaut.attendance.home.domain.repository.AttendanceRepository
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
    fun provideDatabase(): FirebaseDatabase {
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(
        store: FirebaseFirestore,
        database: FirebaseDatabase
    ) : AttendanceRepository {
        return AttendanceRepositoryImpl(
            store = store,
            database = database
        )
    }
}