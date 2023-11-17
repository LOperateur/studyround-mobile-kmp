//
//  iOSSampleBridge.swift
//  iosApp
//
//  Created by Mofe Ejegi on 17/11/2023.
//  Copyright © 2023 L'Operateur Ltd. All rights reserved.
//

import Foundation
import shared

class SampleIOSComponent : SamplePlatformComponent {
    func sayHello(name: String) {
        print("Hello from iOS \(name)")
    }

    func returnGreeting(name: String) -> String {
        return "Hi from iOS \(name)"
    }
}
