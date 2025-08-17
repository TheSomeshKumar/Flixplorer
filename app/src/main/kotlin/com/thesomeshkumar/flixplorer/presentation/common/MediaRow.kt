package com.thesomeshkumar.flixplorer.presentation.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import com.thesomeshkumar.flixplorer.util.Constants
import com.thesomeshkumar.flixplorer.util.toFullImageUrl

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaRow(
    title: String,
    list: LazyPagingItems<MediaListItemUI>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    listItemModifier: Modifier = Modifier,
    onItemClicked: (MediaListItemUI) -> Unit,
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
                    MediaCard(
                        mediaListItemUI = it,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        modifier = listItemModifier,
                        onItemClicked = onItemClicked
                    )
                }
            }
            if (list.loadState.append == LoadState.Loading) {
                item {
                    LoadingView()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    mediaListItemUI: MediaListItemUI,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClicked: (MediaListItemUI) -> Unit,
) {
    with(sharedTransitionScope) {
        ElevatedCard(
            onClick = { onItemClicked(mediaListItemUI) },
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.normal_padding_half))
                .width(dimensionResource(id = R.dimen.home_grid_card_width))
                .height(dimensionResource(id = R.dimen.home_grid_card_height))
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = "poster-${mediaListItemUI.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = Constants.ANIM_TIME_SHORT)
                    }
                )
        ) {
            AsyncImageWithPlaceholder(
                model = mediaListItemUI.posterPath.toFullImageUrl(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
