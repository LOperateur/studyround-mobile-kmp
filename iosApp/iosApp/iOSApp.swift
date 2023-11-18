import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        KoinInitKt.doInitKoin(
            platformComponents: IosPlatformComponents(
                sampleComponent: SampleIOSComponent()
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
