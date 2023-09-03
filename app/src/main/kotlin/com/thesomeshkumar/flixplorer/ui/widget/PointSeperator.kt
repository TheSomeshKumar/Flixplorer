package com.thesomeshkumar.flixplorer.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.util.Constants

@Composable
fun PointSeparator(color: Color = MaterialTheme.colorScheme.onSurface) {
    Text(
        text = Constants.POINT_SEPARATOR,
        color = color,
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.normal_padding_half)
        )
    )
}
