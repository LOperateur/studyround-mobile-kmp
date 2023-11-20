import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinInitIosKt.doInitKoinIos(
            appComponent: IosApplicationComponent(
                networkHelper: IosNetworkHelper()
            )
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
