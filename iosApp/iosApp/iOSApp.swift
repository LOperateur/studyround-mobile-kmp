import GoogleSignIn
import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        KoinInit_iosKt.doInitKoinIos(
            appComponent: IosApplicationComponent(
                networkHelper: IosNetworkHelper(),
                userDefaults: UserDefaults(suiteName: "com.operator.studyround.shared")!,
                googleAuthProvider: IosGoogleAuthProvider()
            )
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                    .onOpenURL { url in
                        GIDSignIn.sharedInstance.handle(url)
                    }
        }
    }
}
