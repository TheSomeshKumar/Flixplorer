package com.thesomeshkumar.flickophile.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.data.datasource.local.AppTheme
import com.thesomeshkumar.flickophile.ui.widget.FlickTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingViewModel: SettingsViewModel = hiltViewModel(),
    onNavigationUp: () -> Unit
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            FlickTopAppBar(title = "Settings", onNavigationUp = { onNavigationUp() })
        }
    ) { paddingValues ->

        val useMaterial3 = settingViewModel.useMaterial3.value.collectAsState(initial = true)
        val useDarkMode =
            settingViewModel.useDarkMode.value.collectAsState(
                initial = AppTheme.FOLLOW_SYSTEM.string
            )

        Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            Column(
                modifier = Modifier
                    .padding(all = dimensionResource(id = R.dimen.normal_padding))
                    .fillMaxSize()
            ) {
                SettingsItem(
                    settingTitle = stringResource(R.string.setting_theme),
                    settingActionComponent = {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Theme",
                            modifier = Modifier.clickable {
                                openBottomSheet = !openBottomSheet
                            }
                        )
                    }
                )
                Divider()

                SettingsItem(
                    settingTitle = stringResource(R.string.setting_material_you),
                    settingActionComponent = {
                        Switch(
                            modifier = Modifier.size(20.dp),
                            checked = useMaterial3.value,
                            onCheckedChange = {
                                settingViewModel.updateUseM3(useMaterial3.value.not())
                            }
                        )
                    }
                )
                Divider()
            }
        }
        if (openBottomSheet) {
            ThemeSelectorBottomSheet(
                openBottomSheet = {
                    openBottomSheet = false
                },
                bottomSheetState = bottomSheetState,
                darkModeState = useDarkMode,
                onModeChange = { selectedMode ->
                    settingViewModel.updateUseDarkMode(selectedMode)
                }
            )
        }
    }
}

@Composable
fun SettingsItem(
    settingTitle: String,
    settingActionComponent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.setting_item_height)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = settingTitle, style = MaterialTheme.typography.bodyLarge)
        settingActionComponent()
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen {}
}
