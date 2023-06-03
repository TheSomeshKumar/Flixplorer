package com.thesomeshkumar.flixplorer.ui.screens.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.data.datasource.local.AppTheme
import com.thesomeshkumar.flixplorer.ui.widget.FlixTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingViewModel: SettingsViewModel = hiltViewModel(),
    onNavigationUp: () -> Unit
) {
    val useMaterial3 = settingViewModel.useMaterial3.value.collectAsState(initial = true)
    val useDarkMode =
        settingViewModel.useDarkMode.value.collectAsState(initial = AppTheme.SYSTEM_DEFAULT.string)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val rotateAnimation = animateFloatAsState(targetValue = if (openBottomSheet) 180f else 0f)
    Scaffold(
        topBar = {
            FlixTopAppBar(
                title = stringResource(R.string.title_settings),
                onNavigationUp = { onNavigationUp() }
            )
        }
    ) { paddingValues ->

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
                            painter = painterResource(id = R.drawable.ic_chevron_down),
                            contentDescription = stringResource(R.string.setting_theme),
                            modifier = Modifier.clickable {
                                openBottomSheet = !openBottomSheet
                            }.rotate(rotateAnimation.value)
                        )
                    }
                )
                Divider()

                SettingsItem(
                    settingTitle = stringResource(R.string.setting_material_you),
                    settingActionComponent = {
                        Switch(
                            modifier = Modifier,
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
                bottomSheetState = bottomSheetState,
                darkModeState = useDarkMode,
                openBottomSheet = { openBottomSheet = false },
                onModeChange = { selectedMode -> settingViewModel.updateUseDarkMode(selectedMode) }
            )
        }
    }
}

@Composable
fun SettingsItem(
    settingTitle: String,
    modifier: Modifier = Modifier,
    settingActionComponent: @Composable () -> Unit
) {
    Row(
        modifier = modifier
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
