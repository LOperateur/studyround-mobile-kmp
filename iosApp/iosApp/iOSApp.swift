import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        KoinInit_iosKt.doInitKoinIos(
            appComponent: IosApplicationComponent(
                networkHelper: IosNetworkHelper(),
                userDefaults: UserDefaults(suiteName: "com.operator.studyround.shared")!
            )
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
