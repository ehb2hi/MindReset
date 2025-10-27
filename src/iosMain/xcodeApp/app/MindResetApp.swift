//
//  mindresetApp.swift
//  mindreset
//
//  Created by alex on 01.05.2024.
//

import SwiftUI
import KMPLib

@main
struct MindResetApp: App {
    var body: some Scene {
        WindowGroup {
            AppView()
                .ignoresSafeArea()
        }
    }
}

struct AppView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        IosAppKt.createAppViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
