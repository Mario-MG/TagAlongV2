package com.mariomg.tagalong.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

// Source: https://www.devbitsandbytes.com/jetpack-compose-a-simple-opiniated-autocompletetextview/
@ExperimentalAnimationApi
@Composable
fun <T> AutoCompleteTextField(
    modifier: Modifier = Modifier,
    query: String,
    queryLabel: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onQueryChange: (String) -> Unit = {},
    predictions: List<T> = emptyList(),
    predictionFilter: (T) -> Boolean = { true },
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = { Text(it.toString()) }
) {
    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    var isSearching by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .onFocusChanged { focusState ->
                isSearching = focusState.isFocused
            }
    ) {
        QuerySearch(
            query = query,
            label = queryLabel,
            leadingIcon = leadingIcon,
            onQueryChange = onQueryChange,
            onDoneActionClick = {
                onDoneActionClick()
            },
            onClearClick = {
                onClearClick()
            }
        )
        AnimatedVisibility(visible = isSearching) {
            LazyColumn(
                state = lazyListState,
                modifier = modifier
                    .heightIn(max = TextFieldDefaults.MinHeight * 2)
                    .padding(vertical = 4.dp)
            ) {
                items(predictions.filter(predictionFilter)) { prediction ->
                    Row(
                        Modifier
                            .clickable {
                                view.clearFocus()
                                onItemClick(prediction)
                            }
                            .padding(2.dp)
                            .fillMaxWidth()
                            .heightIn(min = TextFieldDefaults.MinHeight * 0.5f)
                    ) {
                        itemContent(prediction)
                    }
                }
            }
        }
    }
}