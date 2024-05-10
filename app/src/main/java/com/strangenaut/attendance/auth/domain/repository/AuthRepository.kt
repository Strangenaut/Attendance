package com.strangenaut.attendance.auth.domain.repository

import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Response

interface AuthRepository {

    val currentUserEmail: String?

    val isUserAlreadySignedIn: Boolean

    val isUserEmailVerified: Boolean

    suspend fun signInWithEmailAndPassword(email: String, password: String): Response

    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        passwordRepetition: String
    ): Response

    suspend fun registerAccount(user: User): Response

    suspend fun sendEmailVerification(): Response

    suspend fun reloadFirebaseUser(): Response

    suspend fun sendPasswordResetEmail(email: String): Response

    fun signOut()

    suspend fun revokeAccess(): Response
}