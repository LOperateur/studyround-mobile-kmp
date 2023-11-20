import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin(
            platform: IosPlatform(
                sampleComponent: SampleIOSComponent(),
                networkHelper: IosNetworkHelper()
            ),
            appDeclaration: { _ in}
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
