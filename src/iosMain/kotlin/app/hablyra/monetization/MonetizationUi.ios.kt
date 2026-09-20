package app.hablyra.monetization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
actual fun DashboardAdBanner(modifier: Modifier) = Unit

@Composable
actual fun rememberPrivacyOptionsState(): PrivacyOptionsState =
    remember { PrivacyOptionsState(isRequired = false, show = {}) }
