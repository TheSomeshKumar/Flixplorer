@file:OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)

package com.thesomeshkumar.flixplorer.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.component.ErrorView
import com.thesomeshkumar.flixplorer.ui.component.FlixTopAppBar
import com.thesomeshkumar.flixplorer.ui.component.LoadingView
import com.thesomeshkumar.flixplorer.ui.component.PeopleRow
import com.thesomeshkumar.flixplorer.ui.component.RatingBar
import com.thesomeshkumar.flixplorer.ui.component.VideoRow
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.util.getError
import com.thesomeshkumar.flixplorer.util.openYoutubeLink
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
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        FlixTopAppBar(
            title = name,
            onNavigationUp = onNavigationUp,
            scrollBehavior = scrollBehavior
        )
    }) { paddingValues ->
        val consolidatedDetailUiState = viewModel.uiState.collectAsStateWithLifecycle()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (consolidatedDetailUiState.value) {
                is DetailUiState.Loading -> {
                    LoadingView(
                        modifier = Modifier
                            .fillMaxSize()
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
                    ErrorView(
                        errorText = error,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    backdrop: String,
    poster: String,
    details: DetailUI,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ConstraintLayout {
            val (backdropRef, posterRef, detailRef) = createRefs()
            val backdropHeight = dimensionResource(id = R.dimen.detail_screen_poster_height)
            AsyncImage(
                model = backdrop.toFullImageUrl(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                error = painterResource(id = R.drawable.ic_load_error),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(backdropHeight)
                    .fillMaxWidth()
                    .constrainAs(backdropRef) {}
            )
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .height(backdropHeight.div(1.5f))
                    .width(backdropHeight.div(2f))
                    .padding(start = dimensionResource(id = R.dimen.normal_padding))
                    .constrainAs(posterRef) {
                        top.linkTo(backdropRef.bottom)
                        bottom.linkTo(backdropRef.bottom)
                        start.linkTo(backdropRef.start)
                    }
            ) {
                AsyncImage(
                    model = poster.toFullImageUrl(),
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                    error = painterResource(id = R.drawable.ic_load_error),
                    contentScale = ContentScale.FillBounds
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .constrainAs(detailRef) {
                        top.linkTo(backdropRef.bottom)
                        start.linkTo(posterRef.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                ElevatedAssistChip(onClick = {}, label = { Text(text = details.genres.name) })

                ElevatedAssistChip(onClick = {}, label = { Text(text = details.releaseDate) })

                if (!details.runtime.isNullOrBlank()) {
                    ElevatedAssistChip(leadingIcon = {
                        Icon(
                            Icons.Rounded.Schedule,
                            contentDescription = null
                        )
                    }, onClick = {}, label = { Text(text = details.runtime.toString()) })
                }
                RatingBar(
                    rating = (details.voteAverage / 2).toFloat(),
                    modifier.height(20.dp)
                )
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
                onItemClicked = { context.openYoutubeLink(it.key) }
            )
        }
    }
}
