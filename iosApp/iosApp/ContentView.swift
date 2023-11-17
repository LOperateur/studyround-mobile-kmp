import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    var iosBridge: IosPlatformBridge

    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(iosBridge: iosBridge)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var iosBridge: IosPlatformBridge

    var body: some View {
        ComposeView(iosBridge: iosBridge)
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



