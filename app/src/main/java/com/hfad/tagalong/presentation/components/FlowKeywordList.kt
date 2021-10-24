package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun <T> FlowKeywordList(
    keywordObjects: List<T>,
    onClickDeleteIcon: ((T) -> Unit)? = null
) {
    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        shape = MaterialTheme.shapes.medium
    ) {
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
        ) {
            for (keywordObject in keywordObjects) {
                Keyword(
                    keywordObject = keywordObject,
                    onClickDeleteIcon = onClickDeleteIcon
                )
            }
        }
    }
}