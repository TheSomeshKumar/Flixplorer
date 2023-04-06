package com.thesomeshkumar.flickophile.ui.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.flickophile.data.repository.FlickophileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val flickRepository: FlickophileRepository
) : ViewModel() {

    val useMaterial3: State<Flow<Boolean>> = mutableStateOf(flickRepository.readUseMaterial3())
    val useDarkMode: State<Flow<String>> = mutableStateOf(flickRepository.readUseDarkMode())

    fun updateUseM3(useMaterial3: Boolean) {
        viewModelScope.launch {
            flickRepository.updateUseMaterial3(useMaterial3)
        }
    }

    fun updateUseDarkMode(useDarkMode: String) {
        viewModelScope.launch {
            flickRepository.updateUseDarkMode(useDarkMode)
        }
    }
}
