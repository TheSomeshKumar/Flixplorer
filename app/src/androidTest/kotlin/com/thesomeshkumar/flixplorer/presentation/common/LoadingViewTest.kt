package com.thesomeshkumar.flixplorer.presentation.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.thesomeshkumar.flixplorer.R
import org.junit.Rule
import org.junit.Test

class LoadingViewTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showLoadingViewTest() {
        rule.setContent {
            LoadingView()
        }

        rule.onNodeWithTag("LoadingView")
            .assertExists()
        rule.onNodeWithText(rule.activity.getString(R.string.loading), useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
