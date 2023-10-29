package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
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
import coil.compose.AsyncImage
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.models.PeopleUI
import com.thesomeshkumar.flixplorer.util.toFullImageUrl

@Composable
fun PeopleRow(
    title: String,
    list: List<PeopleUI>,
    modifier: Modifier = Modifier,
    listItemModifier: Modifier = Modifier,
    onItemClicked: (PeopleUI) -> Unit
) {
    Column {
        if (list.isNotEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.normal_padding_half)
                )
            )
        }

        LazyRow(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            state = rememberLazyListState()
        ) {
            items(list.size) { index ->
                PeopleCard(
                    list[index],
                    listItemModifier,
                    onItemClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleCard(
    peopleItem: PeopleUI,
    modifier: Modifier = Modifier,
    onItemClicked: (PeopleUI) -> Unit
) {
    Card(
        onClick = { onItemClicked(peopleItem) },
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .width(dimensionResource(id = R.dimen.home_grid_card_width))
            .height(dimensionResource(id = R.dimen.home_grid_card_height))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = peopleItem.profilePath?.toFullImageUrl(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                error = painterResource(id = R.drawable.ic_load_error),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(
                    dimensionResource(id = R.dimen.home_grid_poster_height)
                )
            )

            Text(
                text = peopleItem.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                    .weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
            Text(
                text = peopleItem.role,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                    .weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
        }
    }
}
