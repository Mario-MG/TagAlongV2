package com.mariomg.tagalong.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.mariomg.tagalong.tag_domain.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TagItemList(
    tags: List<Tag>,
    onClickTagItem: (Tag) -> Unit
) {
    LazyColumn {
        items(items = tags) { tag ->
            TagItemCard(
                tag = tag,
                onClick = {
                    onClickTagItem(tag)
                }
            )
        }
    }
}