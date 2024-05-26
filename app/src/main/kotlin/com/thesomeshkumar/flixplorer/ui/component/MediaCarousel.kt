package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaUI
import com.thesomeshkumar.flixplorer.ui.theme.flix_color_translucent_black
import com.thesomeshkumar.flixplorer.util.Constants
import com.thesomeshkumar.flixplorer.util.carouselTransition
import com.thesomeshkumar.flixplorer.util.toFullImageUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.MediaCarousel(
    list: LazyPagingItems<HomeMediaUI>,
    totalItemsToShow: Int = 10,
    carouselLabel: String = "",
    animatedVisibilityScope: AnimatedVisibilityScope,
    autoScrollDuration: Long = Constants.CAROUSEL_AUTO_SCROLL_TIMER,
    onItemClicked: (HomeMediaUI) -> Unit
) {
    val pageCount = list.itemCount.coerceAtMost(totalItemsToShow)
    val pagerState: PagerState = rememberPagerState(pageCount = { pageCount })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {
                    launch {
                        delay(timeMillis = autoScrollDuration)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(
                                durationMillis = Constants.ANIM_TIME_LONG
                            )
                        )
                        currentPageKey = nextPage
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.double_padding)
                ),
                pageSpacing = dimensionResource(id = R.dimen.normal_padding)
            ) { page: Int ->
                val item: HomeMediaUI? = list[page]
                item?.let {
                    Card(
                        onClick = { onItemClicked(it) },
                        modifier = Modifier.carouselTransition(
                            page,
                            pagerState
                        )
                    ) {
                        CarouselBox(
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
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CarouselBox(
    item: HomeMediaUI,
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
                    state = rememberSharedContentState(key = "backdrop-${item.id}"),
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
