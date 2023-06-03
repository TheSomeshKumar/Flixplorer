package com.thesomeshkumar.flixplorer.ui.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flixRepository: FlixplorerRepository
) : ViewModel() {

    val useMaterial3: State<Flow<Boolean>> = mutableStateOf(flixRepository.readUseMaterial3())
    val useDarkMode: State<Flow<String>> = mutableStateOf(flixRepository.readUseDarkMode())

    fun updateUseM3(useMaterial3: Boolean) {
        viewModelScope.launch {
            flixRepository.updateUseMaterial3(useMaterial3)
        }
    }

    fun updateUseDarkMode(useDarkMode: String) {
        viewModelScope.launch {
            flixRepository.updateUseDarkMode(useDarkMode)
        }
    }
}
