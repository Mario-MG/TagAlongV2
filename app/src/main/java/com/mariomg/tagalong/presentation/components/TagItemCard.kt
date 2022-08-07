package com.mariomg.tagalong.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mariomg.tagalong.R
import com.mariomg.tagalong.tag_domain.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TagItemCard(
    tag: Tag,
    onClick: () -> Unit = {}
) {
    val resources = LocalContext.current.resources

    IconItemCard(
        imageVector = Icons.Filled.Tag,
        title = tag.name,
        subtitle = resources.getQuantityString(R.plurals.number_of_songs, tag.size, tag.size),
        onClick = onClick
    )
}