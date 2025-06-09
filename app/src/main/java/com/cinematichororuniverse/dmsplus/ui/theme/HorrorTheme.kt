package com.cinematichororuniverse.dmsplus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Horror-themed color palette
object HorrorColors {
    val BloodRed = Color(0xFF8B0000)
    val DarkRed = Color(0xFF660000)
    val DeepBlack = Color(0xFF0D0D0D)
    val GhostWhite = Color(0xFFF8F8FF)
    val ShadowGray = Color(0xFF1A1A1A)
    val MidnightBlue = Color(0xFF191970)
    val BoneWhite = Color(0xFFF9F6EE)
    val CrimsonAccent = Color(0xFFDC143C)
    val DarkGray = Color(0xFF2D2D2D)
    val LightGray = Color(0xFF404040)
}

private val DarkColorScheme = darkColorScheme(
    primary = HorrorColors.BloodRed,
    onPrimary = HorrorColors.BoneWhite,
    primaryContainer = HorrorColors.DarkRed,
    onPrimaryContainer = HorrorColors.GhostWhite,
    secondary = HorrorColors.CrimsonAccent,
    onSecondary = HorrorColors.BoneWhite,
    secondaryContainer = HorrorColors.DarkGray,
    onSecondaryContainer = HorrorColors.GhostWhite,
    tertiary = HorrorColors.MidnightBlue,
    onTertiary = HorrorColors.BoneWhite,
    error = HorrorColors.BloodRed,
    errorContainer = HorrorColors.DarkRed,
    onError = HorrorColors.BoneWhite,
    onErrorContainer = HorrorColors.GhostWhite,
    background = HorrorColors.DeepBlack,
    onBackground = HorrorColors.GhostWhite,
    surface = HorrorColors.ShadowGray,
    onSurface = HorrorColors.GhostWhite,
    surfaceVariant = HorrorColors.DarkGray,
    onSurfaceVariant = HorrorColors.BoneWhite,
    outline = HorrorColors.LightGray,
    inverseOnSurface = HorrorColors.DeepBlack,
    inverseSurface = HorrorColors.GhostWhite,
    inversePrimary = HorrorColors.DarkRed,
    surfaceTint = HorrorColors.BloodRed,
    outlineVariant = HorrorColors.DarkGray,
    scrim = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = HorrorColors.DarkRed,
    onPrimary = HorrorColors.BoneWhite,
    primaryContainer = HorrorColors.BloodRed,
    onPrimaryContainer = HorrorColors.GhostWhite,
    secondary = HorrorColors.BloodRed,
    onSecondary = HorrorColors.BoneWhite,
    secondaryContainer = HorrorColors.LightGray,
    onSecondaryContainer = HorrorColors.DeepBlack,
    tertiary = HorrorColors.MidnightBlue,
    onTertiary = HorrorColors.BoneWhite,
    error = HorrorColors.BloodRed,
    errorContainer = HorrorColors.CrimsonAccent,
    onError = HorrorColors.BoneWhite,
    onErrorContainer = HorrorColors.DeepBlack,
    background = HorrorColors.BoneWhite,
    onBackground = HorrorColors.DeepBlack,
    surface = HorrorColors.GhostWhite,
    onSurface = HorrorColors.DeepBlack,
    surfaceVariant = HorrorColors.LightGray,
    onSurfaceVariant = HorrorColors.DeepBlack,
    outline = HorrorColors.DarkGray,
    inverseOnSurface = HorrorColors.GhostWhite,
    inverseSurface = HorrorColors.DeepBlack,
    inversePrimary = HorrorColors.BloodRed,
    surfaceTint = HorrorColors.DarkRed,
    outlineVariant = HorrorColors.LightGray,
    scrim = Color.Black,
)

@Composable
fun HorrorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HorrorTypography,
        content = content
    )
}