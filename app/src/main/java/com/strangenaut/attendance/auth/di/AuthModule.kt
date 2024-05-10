package com.strangenaut.attendance.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.auth.data.repository.AuthRepositoryImpl
import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.auth.domain.usecase.AuthUseCases
import com.strangenaut.attendance.auth.domain.usecase.DeleteCurrentUser
import com.strangenaut.attendance.auth.domain.usecase.GetUserState
import com.strangenaut.attendance.auth.domain.usecase.Register
import com.strangenaut.attendance.auth.domain.usecase.SendPasswordResetEmail
import com.strangenaut.attendance.auth.domain.usecase.SendVerificationEmail
import com.strangenaut.attendance.auth.domain.usecase.SignIn
import com.strangenaut.attendance.auth.domain.usecase.SignUp
import com.strangenaut.attendance.auth.domain.usecase.WaitForButtonToBecomeEnabled
import com.strangenaut.attendance.auth.domain.usecase.WaitForEmailVerification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        app: AttendanceApp,
        auth: FirebaseAuth,
        store: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(
            app = app,
            auth = auth,
            store = store
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            getUserState = GetUserState(authRepository),
            signIn = SignIn(authRepository),
            sendPasswordResetEmail = SendPasswordResetEmail(authRepository),
            waitForButtonToBecomeEnabled = WaitForButtonToBecomeEnabled(),
            signUp = SignUp(authRepository),
            sendVerificationEmail = SendVerificationEmail(authRepository),
            waitForEmailVerification = WaitForEmailVerification(authRepository),
            register = Register(authRepository),
            deleteCurrentUser = DeleteCurrentUser(authRepository)
        )
    }
}