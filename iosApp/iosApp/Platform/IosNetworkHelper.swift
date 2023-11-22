//
//  IosNetworkHelper.swift
//  iosApp
//
//  Created by Mofe Ejegi on 19/11/2023.
//  Copyright © 2023 L'Operateur Ltd. All rights reserved.
//

import Foundation
import Network
import composeApp

class IosNetworkHelper : NetworkHelper {
    private let monitor: NWPathMonitor = NWPathMonitor()

    func registerListener(onNetworkAvailable: @escaping () -> Void, onNetworkLost: @escaping () -> Void) {
        monitor.pathUpdateHandler = { path in
            if path.status == .satisfied {
                onNetworkAvailable()
            } else {
                onNetworkLost()
            }
        }
        monitor.start(queue: DispatchQueue.global(qos: .background))
    }
    

    func unregisterListener() {
        monitor.cancel()
    }
}
