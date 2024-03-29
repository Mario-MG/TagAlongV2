package com.mariomg.tagalong.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mariomg.tagalong.R

@Composable
fun <T : Keyword> Keyword(
    keywordObject: T,
    onClickDeleteIcon: ((T) -> Unit)? = null
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = keywordObject.keyword(),
                modifier = Modifier
                    .padding(
                        vertical = 2.dp,
                        horizontal = 6.dp
                    )
            )
            onClickDeleteIcon?.let {
                IconButton(
                    onClick = {
                          onClickDeleteIcon(keywordObject)
                    },
                    modifier = Modifier
                        .padding(2.dp)
                        .then(Modifier.size(24.dp))
                ) {
                    Icon(
                        Icons.Filled.Cancel,
                        contentDescription = stringResource(R.string.delete_keyword_icon),
                        tint = MaterialTheme.colors.surface
                    )
                }
            }
        }
    }
}

interface Keyword {
    fun keyword(): String
}
