import SwiftUI
import shared

@main
struct iOSApp: App {

	let iosBridge: IosPlatformBridge
	let sampleComponent: SamplePlatformComponent

    init() {
        sampleComponent = SampleIOSComponent()
        iosBridge = IosPlatformBridgeKt.providesIosPlatformBridge(samplePlatformComponent: sampleComponent)
    }

    var body: some Scene {
		WindowGroup {
			ContentView(iosBridge: iosBridge)
		}
	}
}
