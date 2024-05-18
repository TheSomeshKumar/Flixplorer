package com.thesomeshkumar.flixplorer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.thesomeshkumar.flixplorer.data.datasource.local.AppTheme
import com.thesomeshkumar.flixplorer.ui.screens.home.HomeScreen
import com.thesomeshkumar.flixplorer.ui.screens.settings.SettingsViewModel
import com.thesomeshkumar.flixplorer.ui.theme.FlixplorerComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        val viewModel: SettingsViewModel by viewModels()

        setContent {
            val useMaterial3: State<Boolean> = viewModel.useMaterial3.collectAsState(
                initial = true
            )

            val darkThemePref: State<String> = viewModel.useDarkMode.collectAsState(
                initial = AppTheme.SYSTEM_DEFAULT.string
            )

            val useDarkMode = when (darkThemePref.value) {
                AppTheme.LIGHT.string -> false
                AppTheme.DARK.string -> true
                else -> isSystemInDarkTheme()
            }

            val context = LocalContext.current
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    SideEffect {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
            FlixplorerComposeTheme(
                dynamicColor = useMaterial3.value,
                darkTheme = useDarkMode
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(navController = rememberNavController())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    FlixplorerComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen()
            HomeScreen()
        }
    }
}
