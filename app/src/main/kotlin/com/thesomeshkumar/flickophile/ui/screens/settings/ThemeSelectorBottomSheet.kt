package com.thesomeshkumar.flickophile.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.thesomeshkumar.flickophile.data.datasource.local.AppTheme
import com.thesomeshkumar.flickophile.ui.widget.DarkModeRadioGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorBottomSheet(
    bottomSheetState: SheetState,
    darkModeState: State<String>,
    modifier: Modifier = Modifier,
    radioGroupModifier: Modifier = Modifier,
    radioButtonModifier: Modifier = Modifier,
    openBottomSheet: () -> Unit,
    onModeChange: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = openBottomSheet,
        sheetState = bottomSheetState,
        modifier = modifier
    ) {
        DarkModeRadioGroup(
            radioOptions = AppTheme.getList(),
            darkModeState = darkModeState,
            modifier = radioGroupModifier,
            radioButtonModifier = radioButtonModifier,
            onModeChange = onModeChange
        )
    }
}
