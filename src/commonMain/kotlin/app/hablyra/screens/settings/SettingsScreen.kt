package app.hablyra.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.hablyra.design.HablyraTheme
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.monetization.rememberPrivacyOptionsState

@Composable
fun SettingsScreen() {
    val strings = LocalAppEnvironment.current.resources.strings
    val spacing = HablyraTheme.spacing
    val colors = HablyraTheme.colors
    val privacyOptions = rememberPrivacyOptionsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(
                start = spacing.screenHorizontal,
                end = spacing.screenHorizontal,
                top = spacing.space24,
                bottom = spacing.space16
            ),
            text = strings.settingsTitle(),
            color = colors.contentPrimary,
            style = MaterialTheme.typography.headlineLarge
        )
        ListItem(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.space4),
            colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
            headlineContent = { Text(strings.appearanceTitle()) },
            supportingContent = { Text(strings.systemDefaultTheme()) },
            leadingContent = {
                Icon(Icons.Filled.Palette, contentDescription = null, tint = colors.brandPrimary)
            }
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = spacing.space48 + spacing.screenHorizontal),
            color = colors.divider
        )
        if (privacyOptions.isRequired) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = privacyOptions.show)
                    .padding(horizontal = spacing.space4),
                colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
                headlineContent = { Text(strings.privacyChoicesTitle()) },
                leadingContent = {
                    Icon(Icons.Filled.PrivacyTip, contentDescription = null, tint = colors.brandPrimary)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = spacing.space48 + spacing.screenHorizontal),
                color = colors.divider
            )
        }
        ListItem(
            modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.space4),
            colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
            headlineContent = { Text(strings.aboutTitle()) },
            leadingContent = {
                Icon(Icons.Filled.Info, contentDescription = null, tint = colors.brandPrimary)
            }
        )
    }
}
