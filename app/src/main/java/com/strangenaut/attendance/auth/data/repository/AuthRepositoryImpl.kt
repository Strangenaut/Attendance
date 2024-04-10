package com.strangenaut.attendance.auth.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() {
            return auth.currentUser
        }

    override val isUserAlreadySignedIn: Boolean
        get() {
            return auth.currentUser != null
        }

    override val isUserEmailVerified: Boolean
        get() {
            return try {
                auth.currentUser?.isEmailVerified ?: false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Failure("Неверный логин или пароль")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: FirebaseAuthWeakPasswordException) {
        Failure("Слишком слабый пароль. Необходимо минимум 6 символов")
    } catch (e: FirebaseAuthUserCollisionException) {
        Failure("Данный адрес уже зарегистрирован")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun registerAccount(user: User) = try {
        store.collection("users")
            .document(user.email)
            .set(user)
            .await()
        Success(true)
    }  catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Success(true)
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun revokeAccess() = try {
        auth.currentUser?.delete()?.await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }
}