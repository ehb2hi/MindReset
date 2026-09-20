package app.hablyra.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalHablyraColors = staticCompositionLocalOf { LightHablyraColors }
val LocalHablyraSpacing = staticCompositionLocalOf { HablyraSpacing() }

object HablyraTheme {
    val colors: HablyraColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHablyraColors.current

    val spacing: HablyraSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalHablyraSpacing.current
}

@Composable
fun HablyraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkHablyraColors else LightHablyraColors
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colors.brandPrimary,
            onPrimary = colors.contentOnPrimary,
            secondary = colors.brandSecondary,
            onSecondary = colors.contentPrimary,
            tertiary = colors.info,
            onTertiary = colors.contentOnPrimary,
            background = colors.background,
            onBackground = colors.contentPrimary,
            surface = colors.surface,
            onSurface = colors.contentPrimary,
            surfaceVariant = colors.surfaceSubtle,
            onSurfaceVariant = colors.contentSecondary,
            surfaceContainerHighest = colors.surfaceElevated,
            outline = colors.borderSubtle,
            outlineVariant = colors.divider,
            error = colors.error,
            onError = colors.contentOnPrimary,
            errorContainer = colors.errorContainer,
            onErrorContainer = colors.contentPrimary,
            primaryContainer = colors.brandSecondary,
            onPrimaryContainer = colors.contentPrimary
        )
    } else {
        lightColorScheme(
            primary = colors.brandPrimary,
            onPrimary = colors.contentOnPrimary,
            secondary = colors.brandSecondary,
            onSecondary = colors.contentPrimary,
            tertiary = colors.info,
            onTertiary = colors.contentOnPrimary,
            background = colors.background,
            onBackground = colors.contentPrimary,
            surface = colors.surface,
            onSurface = colors.contentPrimary,
            surfaceVariant = colors.surfaceSubtle,
            onSurfaceVariant = colors.contentSecondary,
            surfaceContainerHighest = colors.surfaceElevated,
            outline = colors.borderSubtle,
            outlineVariant = colors.divider,
            error = colors.error,
            onError = colors.contentOnPrimary,
            errorContainer = colors.errorContainer,
            onErrorContainer = colors.contentPrimary,
            primaryContainer = colors.brandSecondary,
            onPrimaryContainer = colors.contentPrimary
        )
    }

    CompositionLocalProvider(
        LocalHablyraColors provides colors,
        LocalHablyraSpacing provides HablyraSpacing()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = HablyraTypography,
            shapes = HablyraShapes,
            content = content
        )
    }
}
