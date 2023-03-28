package com.thesomeshkumar.flickophile.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flickophile.ui.models.MediaHomeUI
import com.thesomeshkumar.flickophile.util.toFullPosterUrl

@Composable
fun MediaGridList(
    list: LazyPagingItems<MediaHomeUI>,
    gridCount: Int,
    onItemClicked: (MediaHomeUI) -> Unit
) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCount),
        horizontalArrangement = Arrangement.Center
    ) {
        items(list.itemCount) { index ->
            list[index]?.let {
                MediaItem(it, onItemClicked)
            }
        }
        if (list.loadState.append == LoadState.Loading) {
            item(span = span) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaItem(mediaHomeUI: MediaHomeUI, onItemClicked: (MediaHomeUI) -> Unit) {
    ElevatedCard(
        onClick = { onItemClicked(mediaHomeUI) },
        modifier = Modifier
            .padding(8.dp)
            .height(220.dp)
    ) {
        Column {
            AsyncImage(
                model = mediaHomeUI.backdropPath.toFullPosterUrl(),
                contentDescription = null,
                modifier = Modifier.height(160.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = mediaHomeUI.name,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium

            )
        }
    }
}
