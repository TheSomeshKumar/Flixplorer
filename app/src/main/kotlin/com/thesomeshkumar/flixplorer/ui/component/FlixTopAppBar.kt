package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlixTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onSettingsClick: (() -> Unit)? = null,
    onNavigationUp: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            onNavigationUp?.let { navigationUpAction ->
                IconButton(onClick = { navigationUpAction() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                3.dp
            )
        ),
        actions = {
            onSettingsClick?.let { settingsClickAction ->
                IconButton(onClick = { settingsClickAction() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}
