package com.strangenaut.attendance.home.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.home.domain.repository.AttendanceRepository
import com.strangenaut.attendance.home.model.Lesson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val store: FirebaseFirestore,
    private val database: FirebaseDatabase
) : AttendanceRepository {

    override suspend fun getUser(email: String) = try {
        val userDocument = store.collection("users").document(email)
        val userInfo = userDocument.get().await()

        val userEmail = userInfo.data?.get("email") ?: throw Exception()
        val name = userInfo.data?.get("name") ?: throw Exception()
        val surname = userInfo.data?.get("surname") ?: throw Exception()
        val school = userInfo.data?.get("school") ?: throw Exception()
        val department = userInfo.data?.get("department") ?: throw Exception()
        val group = userInfo.data?.get("group") ?: throw Exception()

        val user = User(
            email = userEmail.toString(),
            name = name.toString(),
            surname = surname.toString(),
            school = school.toString(),
            department = department.toString(),
            group = group.toString(),
        )

        Success(user)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun getDisciplines(email: String) = try {
        val userDocument = store.collection("users").document(email)
        val userInfo = userDocument.get().await()

        val disciplines = userInfo.data?.get("disciplines")

        val result = if (disciplines == null)
            listOf<String>()
        else {
            disciplines as List<*>
        }

        Success(result)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun getCurrentLesson(email: String) = try {
        val host = email.replace('.', '_')
        val hostingRef = database.getReference("$host/hosting")

        val hostingSnapshot = hostingRef.get().await()

        if (hostingSnapshot != null && hostingSnapshot.hasChildren()) {
            val lessonSnapshot = hostingSnapshot.children.first()
            val lesson = lessonSnapshot.getValue(Lesson::class.java)

            if (lesson != null) {
                Success(lesson)
            } else {
                throw Exception("Непредвиденная ошибка")
            }
        } else {
            Success(false)
        }
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun addDiscipline(user: User, discipline: String) = try {
        val userDocument = store.collection("users").document(user.email)
        val userInfo = userDocument.get().await()

        val disciplines = userInfo.data?.get("disciplines")

        val updatedDisciplines = if (disciplines != null)
            disciplines as List<*>
        else {
            listOf<String>()
        } + listOf(discipline)

        userDocument.update(
            "disciplines",
            updatedDisciplines
        ).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun setCurrentLesson(lesson: Lesson): Response {
        return setLesson(lesson, "hosting")
    }

    override suspend fun removeCurrentLesson(email: String) = try {
        val host = email.replace('.', '_')
        val hostingRef = database.getReference("$host/hosting")

        hostingRef.removeValue().await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun saveHostedLesson(lesson: Lesson): Response {
        return setLesson(lesson, "hosted")
    }

    override suspend fun saveAttendedLesson(lesson: Lesson): Response {
        return setLesson(lesson, "attended")
    }

    private suspend fun setLesson(lesson: Lesson, tag: String) = try {
        val hostingRef = database
            .getReference("${lesson.credentials.host}/$tag/${lesson.credentials.id}")

        hostingRef.setValue(lesson).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure("Отсутствует соединение")
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }
}