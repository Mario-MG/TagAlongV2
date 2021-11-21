package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TagItemList(
    tags: List<Tag>,
    onNavigateToTrackList: (Tag) -> Unit
) {
    LazyColumn {
        items(items = tags) { tag ->
            TagItemCard(
                tag = tag,
                onClick = {
                    onNavigateToTrackList(tag)
                }
            )
        }
    }
}