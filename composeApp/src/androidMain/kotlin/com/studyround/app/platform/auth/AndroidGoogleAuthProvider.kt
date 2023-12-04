package com.studyround.app.platform.auth

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.studyround.app.platform.utils.PlatformContext

class AndroidGoogleAuthProvider(
    private val credentialManager: CredentialManager,
) : GoogleAuthProvider {

    private var googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId("451827618190-ojdu16l1s2pk46bad35qseddtej5ig76.apps.googleusercontent.com")
        .build()

    override suspend fun login(
        context: PlatformContext,
        onAuthResult: (GoogleAuthResult) -> Unit,
        onAuthError: (Throwable) -> Unit,
    ) {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                context = context.androidContext,
                request = request,
            )
            handleSignIn(result, onAuthResult, onAuthError)
        } catch (e: GetCredentialException) {
            onAuthError(Throwable("Failure getting credentials", e))
        }
    }

    private fun handleSignIn(
        result: GetCredentialResponse,
        onAuthResult: (GoogleAuthResult) -> Unit,
        onAuthError: (Throwable) -> Unit,
    ) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and authenticate on our server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        val googleAuthResult = GoogleAuthResult(
                            token = googleIdTokenCredential.idToken,
                            email = googleIdTokenCredential.id,
                            firstName = googleIdTokenCredential.givenName,
                            lastName = googleIdTokenCredential.familyName,
                            avatarUrl = googleIdTokenCredential.profilePictureUri?.toString(),
                        )
                        onAuthResult(googleAuthResult)
                    } catch (e: GoogleIdTokenParsingException) {
                        onAuthError(Throwable("Received an invalid google id token response", e))
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    onAuthError(Throwable("Unexpected type of credential"))
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                onAuthError(Throwable("Unexpected type of credential"))
            }
        }
    }

    override suspend fun logout() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
