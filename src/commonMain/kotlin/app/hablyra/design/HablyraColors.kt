package app.hablyra.design

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class HablyraColors(
    val brandPrimary: Color,
    val brandPrimaryHoverPressed: Color,
    val brandSecondary: Color,
    val brandAccent: Color,
    val background: Color,
    val surface: Color,
    val surfaceSubtle: Color,
    val surfaceElevated: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val contentOnPrimary: Color,
    val borderSubtle: Color,
    val divider: Color,
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val info: Color,
    val infoContainer: Color
)

val LightHablyraColors = HablyraColors(
    brandPrimary = Color(0xFF2E7D6B),
    brandPrimaryHoverPressed = Color(0xFF256A5B),
    brandSecondary = Color(0xFFA7D7C5),
    brandAccent = Color(0xFFFF7A7A),
    background = Color(0xFFF6F8F7),
    surface = Color(0xFFFFFFFF),
    surfaceSubtle = Color(0xFFEEF3F1),
    surfaceElevated = Color(0xFFFFFFFF),
    contentPrimary = Color(0xFF1F2937),
    contentSecondary = Color(0xFF667085),
    contentTertiary = Color(0xFF98A2B3),
    contentOnPrimary = Color(0xFFFFFFFF),
    borderSubtle = Color(0xFFE4E7EC),
    divider = Color(0xFFEAECF0),
    success = Color(0xFF3F8F7C),
    successContainer = Color(0xFFDFF7E9),
    warning = Color(0xFFD99A3D),
    warningContainer = Color(0xFFFFF1D6),
    error = Color(0xFFD95C5C),
    errorContainer = Color(0xFFFCE8E8),
    info = Color(0xFF7C6EE6),
    infoContainer = Color(0xFFEEEAFE)
)

val DarkHablyraColors = HablyraColors(
    brandPrimary = Color(0xFF71C8AE),
    brandPrimaryHoverPressed = Color(0xFF5AB298),
    brandSecondary = Color(0xFF244A40),
    brandAccent = Color(0xFFFF8E8E),
    background = Color(0xFF10191D),
    surface = Color(0xFF162228),
    surfaceSubtle = Color(0xFF1C2B31),
    surfaceElevated = Color(0xFF213239),
    contentPrimary = Color(0xFFF4F7F6),
    contentSecondary = Color(0xFFB7C2BF),
    contentTertiary = Color(0xFF84928E),
    contentOnPrimary = Color(0xFF0C1714),
    borderSubtle = Color(0xFF2B3A40),
    divider = Color(0xFF26353B),
    success = Color(0xFF71C8AE),
    successContainer = Color(0xFF183B32),
    warning = Color(0xFFE8B562),
    warningContainer = Color(0xFF3E311A),
    error = Color(0xFFEF8585),
    errorContainer = Color(0xFF432121),
    info = Color(0xFFA99AF4),
    infoContainer = Color(0xFF2A254A)
)
