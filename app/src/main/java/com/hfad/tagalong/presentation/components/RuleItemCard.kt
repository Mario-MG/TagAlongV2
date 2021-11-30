package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.domain.model.Rule

@ExperimentalFoundationApi
@Composable
fun RuleItemCard(
    rule: Rule,
    onClick: () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 8.dp
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = rule.playlistId, // TODO: Bring playlist name from API
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowKeywordList(
                keywordObjects = rule.tags
            )
        }
    }
}