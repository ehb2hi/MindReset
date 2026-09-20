package app.hablyra.monetization

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class PrivacyOptionsState(
    val isRequired: Boolean,
    val show: () -> Unit
)

@Composable
expect fun DashboardAdBanner(modifier: Modifier = Modifier)

@Composable
expect fun rememberPrivacyOptionsState(): PrivacyOptionsState
