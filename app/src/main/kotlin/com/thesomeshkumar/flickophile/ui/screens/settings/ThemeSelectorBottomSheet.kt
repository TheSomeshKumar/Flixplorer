package com.thesomeshkumar.flickophile.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.thesomeshkumar.flickophile.ui.widget.DarkModeRadioGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorBottomSheet(
    openBottomSheet: () -> Unit,
    bottomSheetState: SheetState,
    darkModeState: State<String>,
    onModeChange: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = openBottomSheet,
        sheetState = bottomSheetState
    ) {
        DarkModeRadioGroup(darkModeState, onModeChange)
    }
}
