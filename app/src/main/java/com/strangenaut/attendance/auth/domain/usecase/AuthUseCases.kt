package com.strangenaut.attendance.auth.domain.usecase

data class AuthUseCases (
    val getUserState: GetUserState,
    val signIn: SignIn,
    val sendPasswordResetEmail: SendPasswordResetEmail,
    val waitForButtonToBecomeEnabled: WaitForButtonToBecomeEnabled,
    val signUp: SignUp,
    val sendVerificationEmail: SendVerificationEmail,
    val waitForEmailVerification: WaitForEmailVerification,
    val register: Register,
    val deleteCurrentUser: DeleteCurrentUser
)