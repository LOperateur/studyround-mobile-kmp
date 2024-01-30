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
import com.studyround.app.BuildConfig
import com.studyround.app.platform.ui.PlatformContext

internal class AndroidGoogleAuthProvider(
    private val credentialManager: CredentialManager,
) : GoogleAuthProvider {

    private var googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
        .build()

    override suspend fun login(
        context: PlatformContext,
    ): GoogleAuthResult {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                context = context.androidContext,
                request = request,
            )
            return handleSignIn(result)
        } catch (e: GetCredentialException) {
            throw Exception("Failure getting credentials", e)
        }
    }

    private fun handleSignIn(result: GetCredentialResponse): GoogleAuthResult {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and authenticate on our server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        return GoogleAuthResult(
                            token = googleIdTokenCredential.idToken,
                            email = googleIdTokenCredential.id,
                            firstName = googleIdTokenCredential.givenName,
                            lastName = googleIdTokenCredential.familyName,
                        )
                    } catch (e: GoogleIdTokenParsingException) {
                        throw Exception("Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    throw Exception("Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                throw Exception("Unexpected type of credential")
            }
        }
    }

    override suspend fun logout() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
