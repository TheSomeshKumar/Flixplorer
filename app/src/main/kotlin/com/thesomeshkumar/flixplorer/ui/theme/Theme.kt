package com.thesomeshkumar.flixplorer.ui.theme

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
    primary = flix_color_light_primary,
    onPrimary = flix_color_light_onPrimary,
    primaryContainer = flix_color_light_primaryContainer,
    onPrimaryContainer = flix_color_light_onPrimaryContainer,
    secondary = flix_color_light_secondary,
    onSecondary = flix_color_light_onSecondary,
    secondaryContainer = flix_color_light_secondaryContainer,
    onSecondaryContainer = flix_color_light_onSecondaryContainer,
    tertiary = flix_color_light_tertiary,
    onTertiary = flix_color_light_onTertiary,
    tertiaryContainer = flix_color_light_tertiaryContainer,
    onTertiaryContainer = flix_color_light_onTertiaryContainer,
    error = flix_color_light_error,
    errorContainer = flix_color_light_errorContainer,
    onError = flix_color_light_onError,
    onErrorContainer = flix_color_light_onErrorContainer,
    background = flix_color_light_background,
    onBackground = flix_color_light_onBackground,
    surface = flix_color_light_surface,
    onSurface = flix_color_light_onSurface,
    surfaceVariant = flix_color_light_surfaceVariant,
    onSurfaceVariant = flix_color_light_onSurfaceVariant,
    outline = flix_color_light_outline,
    inverseOnSurface = flix_color_light_inverseOnSurface,
    inverseSurface = flix_color_light_inverseSurface,
    inversePrimary = flix_color_light_inversePrimary,
    surfaceTint = flix_color_light_surfaceTint,
    outlineVariant = flix_color_light_outlineVariant,
    scrim = flix_color_light_scrim
)

private val DarkColorScheme = darkColorScheme(
    primary = flix_color_dark_primary,
    onPrimary = flix_color_dark_onPrimary,
    primaryContainer = flix_color_dark_primaryContainer,
    onPrimaryContainer = flix_color_dark_onPrimaryContainer,
    secondary = flix_color_dark_secondary,
    onSecondary = flix_color_dark_onSecondary,
    secondaryContainer = flix_color_dark_secondaryContainer,
    onSecondaryContainer = flix_color_dark_onSecondaryContainer,
    tertiary = flix_color_dark_tertiary,
    onTertiary = flix_color_dark_onTertiary,
    tertiaryContainer = flix_color_dark_tertiaryContainer,
    onTertiaryContainer = flix_color_dark_onTertiaryContainer,
    error = flix_color_dark_error,
    errorContainer = flix_color_dark_errorContainer,
    onError = flix_color_dark_onError,
    onErrorContainer = flix_color_dark_onErrorContainer,
    background = flix_color_dark_background,
    onBackground = flix_color_dark_onBackground,
    surface = flix_color_dark_surface,
    onSurface = flix_color_dark_onSurface,
    surfaceVariant = flix_color_dark_surfaceVariant,
    onSurfaceVariant = flix_color_dark_onSurfaceVariant,
    outline = flix_color_dark_outline,
    inverseOnSurface = flix_color_dark_inverseOnSurface,
    inverseSurface = flix_color_dark_inverseSurface,
    inversePrimary = flix_color_dark_inversePrimary,
    surfaceTint = flix_color_dark_surfaceTint,
    outlineVariant = flix_color_dark_outlineVariant,
    scrim = flix_color_dark_scrim
)

@Composable
fun FlixplorerComposeTheme(
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
