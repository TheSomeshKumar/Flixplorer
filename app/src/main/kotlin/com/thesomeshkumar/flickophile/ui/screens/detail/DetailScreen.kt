@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.thesomeshkumar.flickophile.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.ui.widget.ErrorView
import com.thesomeshkumar.flickophile.ui.widget.FlickophileAppBar
import com.thesomeshkumar.flickophile.util.getError
import com.thesomeshkumar.flickophile.util.roundTo
import com.thesomeshkumar.flickophile.util.toFullPosterUrl

@OptIn(ExperimentalMaterial3Api::class)
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

    val detailUIState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlickophileAppBar(
                title = name,
                scrollBehavior = scrollBehavior,
                onNavigationUp = onNavigationUp
            )
        }
    ) { paddingValues ->
        DetailContent(paddingValues, poster, detailUIState)
    }
}

@Composable
fun DetailContent(
    paddingValues: PaddingValues,
    poster: String,
    detailUIState: State<DetailUiState>
) {
    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding(), start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = poster.toFullPosterUrl(),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        when (detailUIState.value) {
            is DetailUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

            is DetailUiState.Success -> {
                with((detailUIState.value as DetailUiState.Success).details) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        genres.forEach { genre ->
                            ElevatedAssistChip(
                                onClick = { },
                                label = { Text(genre.name) }
                            )
                        }

                        ElevatedAssistChip(onClick = { }, label = {
                            Text(text = stringResource(R.string.released_on, releaseDate))
                        })
                        ElevatedAssistChip(onClick = { }, label = {
                            Text(
                                text = stringResource(
                                    R.string.rated,
                                    voteAverage.roundTo(1)
                                )
                            )
                        })
                    }
                    Text(
                        text = this.overview,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            is DetailUiState.Error -> {
                val error =
                    (detailUIState.value as DetailUiState.Error)
                        .remoteSourceException.getError(LocalContext.current)
                ErrorView(errorText = error, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
