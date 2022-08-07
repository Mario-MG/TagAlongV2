package com.mariomg.tagalong.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mariomg.tagalong.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun IconItemCard(
    imageVector: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {},
    cardHeight: Dp = 80.dp
) {
    ItemCard(
        title = title,
        subtitle = subtitle,
        picture = {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(R.string.icon_item_card_description),
                modifier = Modifier
                    .size(cardHeight)
            )
        },
        onClick = onClick,
        cardHeight = cardHeight
    )
}