@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.thesomeshkumar.flixplorer.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.theme.flix_color_translucent_black
import com.thesomeshkumar.flixplorer.ui.widget.ErrorView
import com.thesomeshkumar.flixplorer.ui.widget.FlixMediumAppBar
import com.thesomeshkumar.flixplorer.ui.widget.LoadingView
import com.thesomeshkumar.flixplorer.ui.widget.PeopleRow
import com.thesomeshkumar.flixplorer.ui.widget.PointSeparator
import com.thesomeshkumar.flixplorer.ui.widget.VideoRow
import com.thesomeshkumar.flixplorer.util.getError
import com.thesomeshkumar.flixplorer.util.openYoutubeLink
import com.thesomeshkumar.flixplorer.util.roundTo
import com.thesomeshkumar.flixplorer.util.toFullImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    name: String,
    backdrop: String,
    poster: String,
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigationUp: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        FlixMediumAppBar(
            title = name,
            scrollBehavior = scrollBehavior,
            onNavigationUp = onNavigationUp
        )
    }) { paddingValues ->
        val consolidatedDetailUiState = viewModel.uiState.collectAsStateWithLifecycle()
        Box(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding())
        ) {
            when (consolidatedDetailUiState.value) {
                is DetailUiState.Loading -> {
                    LoadingView(
                        modifier = Modifier.fillMaxSize()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                }

                is DetailUiState.Success -> {
                    val details: DetailUI =
                        (consolidatedDetailUiState.value as DetailUiState.Success).details
                    DetailContent(backdrop, poster, details)
                }

                is DetailUiState.Error -> {
                    val error =
                        (consolidatedDetailUiState.value as DetailUiState.Error).remoteSourceException.getError(
                            LocalContext.current
                        )
                    ErrorView(errorText = error, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    backdrop: String,
    poster: String,
    details: DetailUI,
    modifier: Modifier = Modifier
) {
    val gradient = remember {
        Brush.verticalGradient(listOf(Color.Transparent, flix_color_translucent_black))
    }
    val context = LocalContext.current
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Box {
            val backdropHeight = dimensionResource(id = R.dimen.detail_screen_poster_height)
            AsyncImage(
                model = backdrop.toFullImageUrl(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                error = painterResource(id = R.drawable.ic_load_error),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(backdropHeight).fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient)
                    .height(dimensionResource(id = R.dimen.detail_screen_poster_height) / 2)
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(id = R.dimen.normal_padding)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = details.genres.name,
                    style = MaterialTheme.typography.titleMedium
                )
                PointSeparator()
                Text(
                    text = details.releaseDate,
                    style = MaterialTheme.typography.titleMedium
                )
                PointSeparator()
                Text(
                    text = stringResource(R.string.rated, (details.voteAverage / 2).roundTo(1)),
                    style = MaterialTheme.typography.titleMedium
                )
                if (!details.runtime.isNullOrBlank()) {
                    PointSeparator()
                    Text(
                        text = details.runtime,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Column(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.normal_padding_half)
            )
        ) {
            Text(
                text = details.overview,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.normal_padding_half)
                )
            )

            PeopleRow(
                title = stringResource(R.string.casts),
                list = details.credits.cast,
                onItemClicked = {}
            )

            PeopleRow(
                title = stringResource(R.string.crew),
                list = details.credits.crew,
                onItemClicked = {}
            )

            VideoRow(
                title = stringResource(R.string.trailer),
                list = details.videos,
                onItemClicked = {
                    context.openYoutubeLink(it.key)
                }
            )
        }
    }
}
