import SwiftUI
import composeApp

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
