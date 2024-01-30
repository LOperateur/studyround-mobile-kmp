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
    func login(context: PlatformContext, completionHandler: @escaping (GoogleAuthResult?, Error?) -> Void) {

        GIDSignIn.sharedInstance.signIn(withPresenting: context.rootViewController) { signInResult, error in
            guard error == nil else {
                completionHandler(nil, error)
                return
            }
            guard let signInResult = signInResult else {
                let error = self.error(message: "Unable to obtain sign in details")
                completionHandler(nil, error)
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
                completionHandler(googleAuthResult, nil)
            } else {
                let error = self.error(message: "Missing token or email in Google sign in result")
                completionHandler(nil, error)
            }
        }
    }

    func logout(completionHandler: @escaping (Error?) -> Void) {
        GIDSignIn.sharedInstance.signOut()
        completionHandler(nil)
    }

    private func error(message: String) -> NSError {
        return NSError(domain: "com.studyround.app", code: 0, userInfo: [NSLocalizedDescriptionKey: message])
    }
}
