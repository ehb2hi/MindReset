package epicarchitect.mindreset.environment

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppEnvironment = staticCompositionLocalOf<AppEnvironment> {
    error("LocalAppEnvironment not present")
}