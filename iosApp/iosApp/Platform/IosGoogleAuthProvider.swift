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
                // TODO: Peform onAuthError
                return
            }
            // TODO: Perform onAuthResult
        }
    }

    func logout(completionHandler: @escaping (Error?) -> Void) {
        GIDSignIn.sharedInstance.signOut()
    }
}
