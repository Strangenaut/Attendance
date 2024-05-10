package com.strangenaut.attendance.core.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Lesson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonRepositoryImpl @Inject constructor(
    private val app: AttendanceApp,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : LessonRepository {

    private val currentUserEmail: String
        get() {
            return auth.currentUser?.email?.replace('.', '_') as String
        }

    private var currentLessonDbReference: DatabaseReference? = null
    private var currentLessonListener: ValueEventListener? = null

    private val hostedLessonsDbReference: DatabaseReference
        get() {
            return database.getReference("$currentUserEmail/$HOSTED_LESSONS_REFERENCE")
        }
    private val attendedLessonsDbReference: DatabaseReference
        get() {
            return database.getReference("$currentUserEmail/$ATTENDED_LESSONS_REFERENCE")
        }

    override suspend fun getCurrentLesson(): Response {
        return getCurrentLesson(currentUserEmail)
    }

    override suspend fun getCurrentLesson(hostEmail: String) = try {
        val email = hostEmail.replace('.', '_')
        val hostingRef = database.getReference("$email/$HOSTING_LESSON_REFERENCE")

        val hostingSnapshot = hostingRef.get().await()

        if (hostingSnapshot != null && hostingSnapshot.hasChildren()) {
            val lessonSnapshot = hostingSnapshot.children.first()
            val lesson = lessonSnapshot.getValue(Lesson::class.java)

            if (lesson != null) {
                Success(lesson)
            } else {
                throw Exception(app.getString(R.string.unexpected_error))
            }
        } else {
            Success(false)
        }
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun setCurrentLesson(lesson: Lesson): Response {
        return setHostLesson(lesson, HOSTING_LESSON_REFERENCE)
    }

    override suspend fun setCurrentLesson(
        lesson: Lesson,
        parsedCredentials: Credentials
    ): Response {
        if (lesson.credentials.token != parsedCredentials.token) {
            throw Exception(app.getString(R.string.qr_code_expired))
        }

        return setHostLesson(lesson, HOSTING_LESSON_REFERENCE)
    }

    override fun startCurrentLessonListening(
        onDataChange: (lesson: Lesson) -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    ) {
        currentLessonDbReference =
            database.getReference("$currentUserEmail/$HOSTING_LESSON_REFERENCE")

        currentLessonListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lesson = snapshot.children.first().getValue(Lesson::class.java)
                    ?: throw Exception("Lesson was null")
                onDataChange(lesson)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled(error.message)
            }
        }

        try {
            currentLessonDbReference!!.addValueEventListener(
                currentLessonListener as ValueEventListener
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun stopLessonListening() {
        currentLessonDbReference?.removeEventListener(currentLessonListener!!)

        currentLessonListener = null
        currentLessonDbReference = null
    }

    override suspend fun removeCurrentLesson() = try {
        val hostingRef = database.getReference(
            "$currentUserEmail/$HOSTING_LESSON_REFERENCE/"
        )

        hostingRef.removeValue().await()
        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun saveHostedLesson(lesson: Lesson): Response {
        return setHostLesson(lesson, HOSTED_LESSONS_REFERENCE)
    }

    private suspend fun setHostLesson(lesson: Lesson, tag: String) = try {
        val ref = database.getReference(
            "${lesson.credentials.host}/$tag/${lesson.credentials.id}"
        )

        ref.setValue(lesson).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun saveAttendedLessonCredentials(lesson: Lesson) = try {
        val attendedRef = database.getReference(
            "${currentUserEmail}/$ATTENDED_LESSONS_REFERENCE/${lesson.credentials.id}"
        )

        attendedRef.setValue(lesson.credentials.copy(token = "")).await()

        Success(true)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun getHostedLessons() = try {
        val hostedRef =
            database.getReference("${currentUserEmail}/$HOSTED_LESSONS_REFERENCE/")

        val hostedSnapshot = hostedRef.get().await()

        val hostedLessons = mutableListOf<Lesson>()

        if (hostedSnapshot.hasChildren()) {
            for (lessonSnapshot in hostedSnapshot.children) {
                val lesson = lessonSnapshot.getValue(Lesson::class.java)
                    ?: throw Exception(app.getString(R.string.unexpected_error))

                hostedLessons += lesson
            }
        }

        Success(hostedLessons)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override suspend fun getAttendedLessons() = try {
        val attendedRef =
            database.getReference("${currentUserEmail}/$ATTENDED_LESSONS_REFERENCE/")

        val attendedSnapshot = attendedRef.get().await()

        val attendedLessons = mutableListOf<Lesson>()

        if (attendedSnapshot.hasChildren()) {
            for (credentialsSnapshot in attendedSnapshot.children) {
                val credentials = credentialsSnapshot.getValue(Credentials::class.java)
                    ?: throw Exception(app.getString(R.string.unexpected_error))

                val hostedLessonRef =
                    database.getReference(
                        "${credentials.host}/$HOSTED_LESSONS_REFERENCE/${credentials.id}"
                    )

                var lesson = hostedLessonRef
                    .get()
                    .await()
                    .getValue(Lesson::class.java)

                if (lesson == null) {
                    val hostingLessonRef =
                        database.getReference(
                            "${credentials.host}/$HOSTING_LESSON_REFERENCE/${credentials.id}"
                        )

                    lesson = hostingLessonRef
                        .get()
                        .await()
                        .getValue(Lesson::class.java)
                        ?: throw Exception(app.getString(R.string.unexpected_error))
                }

                attendedLessons += lesson
            }
        }

        Success(attendedLessons)
    } catch (e: FirebaseNetworkException) {
        Failure(app.getString(R.string.no_connection))
    } catch (e: Exception) {
        e.printStackTrace()
        Failure("")
    }

    override fun startPastLessonsListening(
        onDataChange: () -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    ) {
        val lessonValueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChange()
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled(error.message)
            }
        }

        try {
            hostedLessonsDbReference.addValueEventListener(lessonValueEventListener)
            attendedLessonsDbReference.addValueEventListener(lessonValueEventListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {

        private const val HOSTING_LESSON_REFERENCE = "hosting"
        private const val HOSTED_LESSONS_REFERENCE = "hosted"
        private const val ATTENDED_LESSONS_REFERENCE = "attended"
    }
}