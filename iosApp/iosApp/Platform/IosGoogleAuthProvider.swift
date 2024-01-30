//
//  IosGoogleAuthProvider.swift
//  iosApp
//
//  Created by Mofe Ejegi on 03/12/2023.
//  Copyright © 2023 L'Operateur Ltd. All rights reserved.
//

import Foundation
import GoogleSignIn
import composeApp

class IosGoogleAuthProvider: GoogleAuthProvider {
    func login(context: PlatformContext, onAuthResult: @escaping (GoogleAuthResult) -> Void, onAuthError: @escaping (KotlinThrowable) -> Void, completionHandler: @escaping (Error?) -> Void) {

        GIDSignIn.sharedInstance.signIn(withPresenting: context.rootViewController) { signInResult, error in
            guard error == nil else {
                onAuthError(KotlinThrowable(message: error?.localizedDescription))
                completionHandler(nil)
                return
            }
            guard let signInResult = signInResult else {
                onAuthError(KotlinThrowable(message: "Unable to obtain sign in details"))
                completionHandler(nil)
                return
            }

            let user = signInResult.user
            if let idToken = user.idToken?.tokenString, let email = user.profile?.email {
                let googleAuthResult = GoogleAuthResult(
                    token: idToken,
                    email: email,
                    firstName: user.profile?.givenName,
                    lastName: user.profile?.familyName
                )
                onAuthResult(googleAuthResult)
            } else {
                onAuthError(KotlinThrowable(message: "Missing token or email in Google sign in result"))
            }
            completionHandler(nil)
        }
    }

    func logout(completionHandler: @escaping (Error?) -> Void) {
        GIDSignIn.sharedInstance.signOut()
        completionHandler(nil)
    }
}
