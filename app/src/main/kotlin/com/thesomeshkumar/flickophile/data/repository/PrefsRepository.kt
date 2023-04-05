package com.thesomeshkumar.flickophile.data.repository

import com.thesomeshkumar.flickophile.data.datasource.local.UserPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PrefsRepository @Inject constructor(
    private val userPreferences: UserPreferences
) {
    fun readUseMaterial3(): Flow<Boolean> = userPreferences.useMaterial3
    fun readUseDarkMode(): Flow<String> = userPreferences.useDarkMode

    suspend fun updateUseMaterial3(useMaterial3: Boolean) {
        userPreferences.updateUseMaterial3(useMaterial3)
    }

    suspend fun updateUseDarkMode(useDarkMode: String) {
        userPreferences.updateUseDarkMode(useDarkMode)
    }
}
