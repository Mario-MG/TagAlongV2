package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.hfad.tagalong.R

// Source: https://www.devbitsandbytes.com/jetpack-compose-a-simple-opiniated-autocompletetextview/
@Composable
fun QuerySearch(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChange: (String) -> Unit
) {
    var showClearButton by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                showClearButton = focusState.isFocused
            },
        value = query,
        onValueChange = onQueryChange,
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        trailingIcon = {
            if (showClearButton) {
                IconButton(onClick = { onClearClick() }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.clear_button_description))
                }
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneActionClick()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}