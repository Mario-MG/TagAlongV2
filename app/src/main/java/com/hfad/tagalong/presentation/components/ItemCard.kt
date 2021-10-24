package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.util.DEFAULT_ALBUM_IMAGE
import com.hfad.tagalong.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ItemCard(
    imageUrl: String?,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    val cardHeight = 80.dp
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp)
            .height(cardHeight)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Row {
            val image = if (imageUrl != null) { // TODO: Move null check to loadPicture function?
                loadPicture(url = imageUrl, defaultImage = DEFAULT_ALBUM_IMAGE).value
            } else {
                loadPicture(drawable = DEFAULT_ALBUM_IMAGE).value
            }
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(cardHeight),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onSurface.copy(0.5f)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}