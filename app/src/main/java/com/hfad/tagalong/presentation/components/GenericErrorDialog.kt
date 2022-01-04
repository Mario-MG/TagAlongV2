package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.util.DialogData

@Composable
fun GenericErrorDialog(dialogData: DialogData) {
    AlertDialog(
        onDismissRequest = dialogData.onDismiss,
        title = {
            Text(dialogData.title)
        },
        text = {
            Text(dialogData.description)
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = dialogData.onDismiss) {
                    Text(stringResource(R.string.generic_error_dialog_ok))
                }
            }
        }
    )
}