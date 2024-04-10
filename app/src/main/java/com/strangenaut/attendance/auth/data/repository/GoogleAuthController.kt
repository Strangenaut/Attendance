package com.strangenaut.attendance.auth.data.repository

//@Singleton
//class GoogleAuthController @Inject constructor(
//    private val application: AttendanceApp,
//    private val oneTapClient: SignInClient,
//    private val auth: FirebaseAuth
//) {
//
//    suspend fun signIn(): IntentSender? {
//        val result = try {
//            oneTapClient.beginSignIn(
//                buildSignInRequest()
//            ).await()
//        } catch(e: Exception) {
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//            null
//        }
//        return result?.pendingIntent?.intentSender
//    }
//
//    suspend fun signInWithIntent(intent: Intent): AuthResult {
//        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
//        val googleIdToken = credential.googleIdToken
//        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
//        return try {
//            val user = auth.signInWithCredential(googleCredentials).await().user
//            AuthResult(
//                data = user?.run {
//                    User(
//                        userId = uid,
//                        username = displayName,
//                    )
//                },
//                errorMessage = null
//            )
//        } catch(e: Exception) {
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//            AuthResult(
//                data = null,
//                errorMessage = e.message
//            )
//        }
//    }
//
//    suspend fun signOut() {
//        try {
//            oneTapClient.signOut().await()
//            auth.signOut()
//        } catch(e: Exception) {
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//        }
//    }
//
//    fun getSignedInUser(): User? = auth.currentUser?.run {
//        User(
//            userId = uid,
//            username = displayName,
//        )
//    }
//
//    private fun buildSignInRequest(): BeginSignInRequest {
//        return BeginSignInRequest.Builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setFilterByAuthorizedAccounts(false)
//                    .setServerClientId(application.applicationContext.getString(R.string.web_client_id))
//                    .build()
//            )
//            .setAutoSelectEnabled(true)
//            .build()
//    }
//}