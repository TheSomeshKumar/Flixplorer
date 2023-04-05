package com.thesomeshkumar.flickophile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thesomeshkumar.flickophile.data.datasource.local.AppTheme
import com.thesomeshkumar.flickophile.ui.screens.home.HomeScreen
import com.thesomeshkumar.flickophile.ui.screens.settings.SettingsViewModel
import com.thesomeshkumar.flickophile.ui.theme.FlickophileComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SettingsViewModel by viewModels()

        setContent {
            val useMaterial3: State<Boolean> = viewModel.useMaterial3.value.collectAsState(
                initial = true
            )
            val darkThemePref: State<String> = viewModel.useDarkMode.value.collectAsState(
                initial = AppTheme.FOLLOW_SYSTEM.string
            )
            val useDarkMode = when (darkThemePref.value) {
                AppTheme.LIGHT.string -> false
                AppTheme.DARK.string -> true
                else -> isSystemInDarkTheme()
            }
            FlickophileComposeTheme(dynamicColor = useMaterial3.value, darkTheme = useDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(navController = rememberAnimatedNavController())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    FlickophileComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen()
        }
    }
}
