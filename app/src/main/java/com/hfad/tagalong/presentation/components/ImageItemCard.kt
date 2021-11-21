package com.hfad.tagalong.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ImageItemCard(
    imageUrl: String?,
    @DrawableRes defaultImage: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {},
    cardHeight: Dp = 80.dp
) {
    ItemCard(
        title = title,
        subtitle = subtitle,
        picture = {
            val image = loadPicture(url = imageUrl, defaultImage = defaultImage).value
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
        },
        onClick = onClick,
        cardHeight = cardHeight
    )
}