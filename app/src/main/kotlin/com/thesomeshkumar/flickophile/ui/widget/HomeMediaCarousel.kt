package com.thesomeshkumar.flickophile.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.ui.theme.flick_color_translucent_black
import com.thesomeshkumar.flickophile.util.Constants
import com.thesomeshkumar.flickophile.util.carouselTransition
import com.thesomeshkumar.flickophile.util.toFullPosterUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeMediaCarousel(
    list: LazyPagingItems<HomeMediaItemUI>,
    onItemClicked: (HomeMediaItemUI) -> Unit
) {
    val pageCount = list.itemCount.coerceAtMost(10)
    val pagerState = rememberPagerState()
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var key by remember { mutableStateOf(0) }
                LaunchedEffect(key1 = key) {
                    launch {
                        delay(timeMillis = Constants.CAROUSEL_AUTO_SCROLL_TIMER)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(page = nextPage)
                        key = nextPage
                    }
                }
            }
        }
    }

    Box {
        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.double_padding)
            ),
            pageSpacing = dimensionResource(id = R.dimen.small_padding)
        ) { page ->
            val item: HomeMediaItemUI = list[page]!!
            Card(
                onClick = { onItemClicked(item) },
                modifier = Modifier
                    .carouselTransition(page, pagerState)
            ) {
                CarouselItem(item)
            }
        }

        Row(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dot_indicator_row_height))
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dot_indicator_padding))
                        .clip(CircleShape)
                        .background(color)
                        .size(dimensionResource(id = R.dimen.dot_indicator_size))

                )
            }
        }
    }
}

@Composable
fun CarouselItem(item: HomeMediaItemUI) {
    Box {
        AsyncImage(
            model = item.backdropPath.toFullPosterUrl(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_load_placeholder),
            error = painterResource(id = R.drawable.ic_load_error),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.home_grid_poster_height))
                .fillMaxWidth()
        )
        val gradient =
            Brush.verticalGradient(listOf(Color.Transparent, flick_color_translucent_black))

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
                    vertical = dimensionResource(id = R.dimen.double_padding)
                )
        )
    }
}
