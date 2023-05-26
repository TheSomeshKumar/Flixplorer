package com.thesomeshkumar.flickophile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = flick_color_light_primary,
    onPrimary = flick_color_light_onPrimary,
    primaryContainer = flick_color_light_primaryContainer,
    onPrimaryContainer = flick_color_light_onPrimaryContainer,
    secondary = flick_color_light_secondary,
    onSecondary = flick_color_light_onSecondary,
    secondaryContainer = flick_color_light_secondaryContainer,
    onSecondaryContainer = flick_color_light_onSecondaryContainer,
    tertiary = flick_color_light_tertiary,
    onTertiary = flick_color_light_onTertiary,
    tertiaryContainer = flick_color_light_tertiaryContainer,
    onTertiaryContainer = flick_color_light_onTertiaryContainer,
    error = flick_color_light_error,
    errorContainer = flick_color_light_errorContainer,
    onError = flick_color_light_onError,
    onErrorContainer = flick_color_light_onErrorContainer,
    background = flick_color_light_background,
    onBackground = flick_color_light_onBackground,
    surface = flick_color_light_surface,
    onSurface = flick_color_light_onSurface,
    surfaceVariant = flick_color_light_surfaceVariant,
    onSurfaceVariant = flick_color_light_onSurfaceVariant,
    outline = flick_color_light_outline,
    inverseOnSurface = flick_color_light_inverseOnSurface,
    inverseSurface = flick_color_light_inverseSurface,
    inversePrimary = flick_color_light_inversePrimary,
    surfaceTint = flick_color_light_surfaceTint,
    outlineVariant = flick_color_light_outlineVariant,
    scrim = flick_color_light_scrim
)

private val DarkColorScheme = darkColorScheme(
    primary = flick_color_dark_primary,
    onPrimary = flick_color_dark_onPrimary,
    primaryContainer = flick_color_dark_primaryContainer,
    onPrimaryContainer = flick_color_dark_onPrimaryContainer,
    secondary = flick_color_dark_secondary,
    onSecondary = flick_color_dark_onSecondary,
    secondaryContainer = flick_color_dark_secondaryContainer,
    onSecondaryContainer = flick_color_dark_onSecondaryContainer,
    tertiary = flick_color_dark_tertiary,
    onTertiary = flick_color_dark_onTertiary,
    tertiaryContainer = flick_color_dark_tertiaryContainer,
    onTertiaryContainer = flick_color_dark_onTertiaryContainer,
    error = flick_color_dark_error,
    errorContainer = flick_color_dark_errorContainer,
    onError = flick_color_dark_onError,
    onErrorContainer = flick_color_dark_onErrorContainer,
    background = flick_color_dark_background,
    onBackground = flick_color_dark_onBackground,
    surface = flick_color_dark_surface,
    onSurface = flick_color_dark_onSurface,
    surfaceVariant = flick_color_dark_surfaceVariant,
    onSurfaceVariant = flick_color_dark_onSurfaceVariant,
    outline = flick_color_dark_outline,
    inverseOnSurface = flick_color_dark_inverseOnSurface,
    inverseSurface = flick_color_dark_inverseSurface,
    inversePrimary = flick_color_dark_inversePrimary,
    surfaceTint = flick_color_dark_surfaceTint,
    outlineVariant = flick_color_dark_outlineVariant,
    scrim = flick_color_dark_scrim
)

@Composable
fun FlickophileComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
            window.navigationBarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
