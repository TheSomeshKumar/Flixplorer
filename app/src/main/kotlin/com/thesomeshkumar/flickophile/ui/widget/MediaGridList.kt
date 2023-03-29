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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.util.toFullPosterUrl

@Composable
fun MediaGridList(
    list: LazyPagingItems<HomeMediaItemUI>,
    gridCount: Int,
    onItemClicked: (HomeMediaItemUI) -> Unit
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
fun MediaItem(homeMediaItemUI: HomeMediaItemUI, onItemClicked: (HomeMediaItemUI) -> Unit) {
    ElevatedCard(
        onClick = { onItemClicked(homeMediaItemUI) },
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .height(dimensionResource(id = R.dimen.home_grid_card_height))
    ) {
        Column {
            AsyncImage(
                model = homeMediaItemUI.backdropPath.toFullPosterUrl(),
                contentDescription = null,
                modifier = Modifier.height(dimensionResource(id = R.dimen.home_grid_poster_height)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = homeMediaItemUI.name,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium

            )
        }
    }
}
