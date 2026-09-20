package app.hablyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.hablyra.screens.root.RootScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        monetizationManager.gatherConsent(this)
        setContent {
            RootScreen(
                environment = appEnvironment
            )
        }
    }
}