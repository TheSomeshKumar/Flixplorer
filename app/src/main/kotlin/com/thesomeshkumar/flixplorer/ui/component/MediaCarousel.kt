package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaModel
import com.thesomeshkumar.flixplorer.ui.theme.flix_color_translucent_black
import com.thesomeshkumar.flixplorer.util.Constants
import com.thesomeshkumar.flixplorer.util.toFullImageUrl

// Define your color if it's not part of MaterialTheme.colorScheme
val flix_color_translucent_black = Color(0x99000000)

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.MediaCarouselM3(
    list: LazyPagingItems<HomeMediaModel>,
    totalItemsToShow: Int = 20,
    carouselLabel: String = "",
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClicked: (HomeMediaModel) -> Unit
) {
    val itemCount = list.itemCount.coerceAtMost(totalItemsToShow)
    val carouselState = rememberCarouselState(initialItem = 0) { itemCount }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.home_grid_poster_height)), // Adjust height as needed
                itemSpacing = dimensionResource(id = R.dimen.normal_padding),
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.double_padding)
                ),
                // This makes items snap into place when scrolled
                preferredItemWidth = 300.dp
            ) { itemIndex: Int ->
                val item: HomeMediaModel? = list[itemIndex]
                item?.let {
                    Card(
                        onClick = { onItemClicked(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .maskClip(MaterialTheme.shapes.medium) // Apply a shape if desired
                    ) {
                        CarouselBoxM3(
                            animatedVisibilityScope = animatedVisibilityScope,
                            item = it
                        )
                    }
                }
            }
        }
        if (carouselLabel.isNotBlank()) {
            Text(
                text = carouselLabel,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.normal_padding))
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CarouselBoxM3(
    item: HomeMediaModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box {
        AsyncImage(
            model = item.backdropPath.toFullImageUrl(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_load_placeholder),
            error = painterResource(id = R.drawable.ic_load_error),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.home_grid_poster_height)
                )
                .fillMaxWidth()
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = "backdrop-${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = Constants.ANIM_TIME_SHORT)
                    }
                )
        )
        val gradient = remember {
            Brush.verticalGradient(
                listOf(
                    Color.Transparent,
                    flix_color_translucent_black
                )
            )
        }
        Text(
            text = item.name,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(gradient)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.normal_padding),
                    vertical = dimensionResource(id = R.dimen.small_padding)
                )
        )
    }
}
