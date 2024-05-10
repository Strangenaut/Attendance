package com.strangenaut.attendance.core.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val app: AttendanceApp,
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) : UserRepository {

    private val currentUserEmail: String
        get() {
            return auth.currentUser?.email ?: ""
        }
    private val userDocument: DocumentReference
        get() {
            return store.collection(USERS_COLLECTION_PATH).document(currentUserEmail)
        }

    override suspend fun getUser() = try {
        val userInfo = userDocument.get().await()

        val email = (userInfo.data?.get(EMAIL_ATTRIBUTE) ?: "") as String
        val name = (userInfo.data?.get(NAME_ATTRIBUTE) ?: "") as String
        val surname = (userInfo.data?.get(SURNAME_ATTRIBUTE) ?: "") as String
        val school = (userInfo.data?.get(SCHOOL_ATTRIBUTE) ?: "") as String
        val department = (userInfo.data?.get(DEPARTMENT_ATTRIBUTE) ?: "") as String
        val group = (userInfo.data?.get(GROUP_ATTRIBUTE) ?: "") as String

        val user = User(
            email = email,
            name = name,
            surname = surname,
            school = school,
            department = department,
            group = group,
        )

        Success(user)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun getDisciplines() = try {
        val disciplines = retrieveDisciplinesSet()

        Success(disciplines)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun addDiscipline(discipline: String) = try {
        val disciplines = retrieveDisciplinesSet()

        if (disciplines.contains(discipline)) {
            throw DisciplineCollisionException()
        }

        val updatedDisciplines = disciplines.toList() + discipline

        userDocument.update(DISCIPLINES_COLLECTION_PATH, updatedDisciplines).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: DisciplineCollisionException) {
        Failure(app.getString(R.string.discipline_exists))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun removeDiscipline(discipline: String) = try {
        val updatedDisciplines =
            (retrieveDisciplinesSet() - discipline).toList()

        userDocument.update(DISCIPLINES_COLLECTION_PATH, updatedDisciplines).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    private suspend fun retrieveDisciplinesSet(): Set<String> {
        val userInfo = userDocument.get().await()

        val disciplines = userInfo.data?.get(DISCIPLINES_COLLECTION_PATH)

        return if (disciplines == null)
            setOf()
        else {
            (disciplines as List<*>)
                .filterIsInstance(String::class.java)
                .toSet()
        }
    }

    companion object {

        private const val USERS_COLLECTION_PATH = "users"
        private const val DISCIPLINES_COLLECTION_PATH = "disciplines"
        private const val EMAIL_ATTRIBUTE = "email"
        private const val NAME_ATTRIBUTE = "name"
        private const val SURNAME_ATTRIBUTE = "surname"
        private const val SCHOOL_ATTRIBUTE = "school"
        private const val DEPARTMENT_ATTRIBUTE = "department"
        private const val GROUP_ATTRIBUTE = "group"
    }
}