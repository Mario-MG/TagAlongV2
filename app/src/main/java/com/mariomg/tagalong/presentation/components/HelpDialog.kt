package com.mariomg.tagalong.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mariomg.tagalong.R

@Composable
fun HelpDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.small,
        ) {
            Column(
                modifier = Modifier
                    .sizeIn(
                        minWidth = 240.dp,
                        minHeight = 160.dp
                    )
                    .padding(
                        horizontal = 22.dp,
                        vertical = 14.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                content()
                Spacer(Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.help_dialog_ok).toUpperCase(Locale.current),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onDismissRequest() },
                    color = MaterialTheme.colors.onSurface.copy(0.5f),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
