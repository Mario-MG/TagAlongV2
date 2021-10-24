package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.hfad.tagalong.presentation.theme.SpotifyGreen300

@Composable
fun TagManager(
    tags: List<String>
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
        FlowRow {
            tags.forEach { tag -> // TODO: Extract Tag composable
                Surface(
                    color = SpotifyGreen300,
                    modifier = Modifier
                        .padding(4.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier
                            .padding(
                                vertical = 2.dp,
                                horizontal = 4.dp
                            )
                    )
                }
            }
        }
    }
}