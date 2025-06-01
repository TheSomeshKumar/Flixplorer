package com.thesomeshkumar.flixplorer.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.thesomeshkumar.flixplorer.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(id = R.dimen.normal_padding))
    ) {
        CircularWavyProgressIndicator()
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
fun LoadingViewPreview() {
    LoadingView()
}
