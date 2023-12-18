package com.studyround.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen  = installSplashScreen()

//        var currentDestination: Destination? = null
//        splashScreen.setKeepOnScreenCondition {
//            currentDestination is PlaceHolder()
//        }

        // Draw from edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
//            SideEffect {
//                currentDestination = Navigator()?.destination
//            }
            App()
        }
    }
}
