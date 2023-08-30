package com.denicks21.recorder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = LightYellow,
    primaryVariant = LightYellow,
    secondary = LightGrey,
    background = LightYellow,
    surface = DarkSurface,
    onPrimary = DarkGrey,
    onSecondary = LightYellow,
    onBackground = DarkGrey,
    onSurface = LightYellow,
    error = Refuse
)

private val LightColorPalette = lightColors(
    primary = DarkGrey,
    primaryVariant = DarkGrey,
    secondary = DarkYellow,
    background = DarkGrey,
    surface = LightSurface,
    onPrimary = LightYellow,
    onSecondary = DarkGrey,
    onBackground = LightYellow,
    onSurface = DarkGrey,
    error = Refuse
)

@Composable
fun RecorderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}