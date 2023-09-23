package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaUI
import com.thesomeshkumar.flixplorer.util.toFullImageUrl

@Composable
fun MediaRow(
    title: String,
    list: LazyPagingItems<HomeMediaUI>,
    modifier: Modifier = Modifier,
    listItemModifier: Modifier = Modifier,
    onItemClicked: (HomeMediaUI) -> Unit
) {
    Column {
        if (list.itemCount > 0) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.normal_padding_half))
            )
        }

        LazyRow(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            state = rememberLazyListState()
        ) {
            items(list.itemCount) { index ->
                list[index]?.let {
                    MediaCard(it, listItemModifier, onItemClicked)
                }
            }
            if (list.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaCard(
    homeMediaUI: HomeMediaUI,
    modifier: Modifier = Modifier,
    onItemClicked: (HomeMediaUI) -> Unit
) {
    ElevatedCard(
        onClick = { onItemClicked(homeMediaUI) },
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .width(dimensionResource(id = R.dimen.home_grid_card_width))
            .height(dimensionResource(id = R.dimen.home_grid_card_height))
    ) {
        Column {
            AsyncImage(
                model = homeMediaUI.posterPath.toFullImageUrl(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                error = painterResource(id = R.drawable.ic_load_error),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(dimensionResource(id = R.dimen.home_grid_poster_height))
            )
            Text(
                text = homeMediaUI.name,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2

            )
        }
    }
}
