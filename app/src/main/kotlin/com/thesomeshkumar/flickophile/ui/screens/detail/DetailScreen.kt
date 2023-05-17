@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.thesomeshkumar.flickophile.ui.screens.detail

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.ui.models.DetailUI
import com.thesomeshkumar.flickophile.ui.widget.ErrorView
import com.thesomeshkumar.flickophile.ui.widget.FlickMediumAppBar
import com.thesomeshkumar.flickophile.ui.widget.LoadingView
import com.thesomeshkumar.flickophile.ui.widget.PointSeparator
import com.thesomeshkumar.flickophile.util.getError
import com.thesomeshkumar.flickophile.util.roundTo
import com.thesomeshkumar.flickophile.util.toFullPosterUrl

@Composable
fun DetailsScreen(
    name: String,
    poster: String,
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigationUp: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        FlickMediumAppBar(
            title = name,
            scrollBehavior = scrollBehavior,
            onNavigationUp = onNavigationUp
        )
    }) { paddingValues ->
        val detailUIState = viewModel.uiState.collectAsStateWithLifecycle()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when (detailUIState.value) {
                is DetailUiState.Loading -> {
                    LoadingView(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                }

                is DetailUiState.Success -> {
                    val details = (detailUIState.value as DetailUiState.Success).details
                    DetailContent(poster, details)
                }

                is DetailUiState.Error -> {
                    val error =
                        (detailUIState.value as DetailUiState.Error).remoteSourceException.getError(
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
    poster: String,
    details: DetailUI,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = poster.toFullPosterUrl(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_load_placeholder),
            error = painterResource(id = R.drawable.ic_load_error),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.detail_screen_poster_height))
                .fillMaxWidth()
        )

        Column(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.normal_padding_half)
            )
        ) {
            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.normal_padding_half)
                )
            ) {
                Text(text = details.genres.name)
                PointSeparator()
                Text(text = details.releaseDate)
                PointSeparator()
                Text(text = stringResource(R.string.rated, (details.voteAverage / 2).roundTo(1)))
                if (!details.runtime.isNullOrBlank()) {
                    PointSeparator()
                    Text(text = details.runtime)
                }
            }

            Divider(
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.normal_padding_half)
                )
            )

            Text(
                text = details.overview,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
