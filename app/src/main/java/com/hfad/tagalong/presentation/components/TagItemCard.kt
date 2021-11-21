package com.hfad.tagalong.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TagItemCard(
    tag: Tag,
    onClick: () -> Unit = {}
) {
    IconItemCard(
        imageVector = Icons.Filled.Tag,
        title = tag.name,
        subtitle = "${tag.size} songs", // TODO: Handle singular/plural
        onClick = onClick
    )
}