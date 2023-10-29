package com.thesomeshkumar.flixplorer.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import com.thesomeshkumar.flixplorer.ui.models.VideoUI
import com.thesomeshkumar.flixplorer.util.toYoutubeThumbUrl

@Composable
fun VideoRow(
    title: String,
    list: List<VideoUI>,
    modifier: Modifier = Modifier,
    listItemModifier: Modifier = Modifier,
    onItemClicked: (VideoUI) -> Unit
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
                VideoThumbCard(
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
fun VideoThumbCard(
    videoItem: VideoUI,
    modifier: Modifier = Modifier,
    onItemClicked: (VideoUI) -> Unit
) {
    Card(
        onClick = { onItemClicked(videoItem) },
        modifier = modifier
            .width(dimensionResource(id = R.dimen.video_item_width))
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = videoItem.key.toYoutubeThumbUrl(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(
                        dimensionResource(id = R.dimen.video_item_height)
                    )
                )

                Image(
                    painter = painterResource(android.R.drawable.ic_media_play),
                    contentDescription = ""
                )
            }

            Text(
                text = videoItem.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.small_padding)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2

            )
        }
    }
}
