package com.strangenaut.attendance.auth.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.auth.domain.repository.PasswordsDoNotMatchException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val app: AttendanceApp,
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) : AuthRepository {

    override val currentUserEmail: String?
        get() {
            return auth.currentUser?.email
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
        Failure(app.getString(R.string.no_connection))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Failure(app.getString(R.string.wrong_login_or_password))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        passwordRepetition: String
    ) = try {
        if (password != passwordRepetition) {
            throw PasswordsDoNotMatchException()
        }

        auth.createUserWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: FirebaseAuthWeakPasswordException) {
        Failure(app.getString(R.string.weak_password))
    } catch (e: FirebaseAuthUserCollisionException) {
        Failure(app.getString(R.string.address_exists))
    } catch (e: PasswordsDoNotMatchException) {
        Failure(app.getString(R.string.passwords_do_not_match))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun registerAccount(user: User) = try {
        store.collection(USERS_COLLECTION_PATH)
            .document(user.email)
            .set(user)
            .await()
        Success(true)
    }  catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
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
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    companion object {

        private const val USERS_COLLECTION_PATH = "users"
    }
}