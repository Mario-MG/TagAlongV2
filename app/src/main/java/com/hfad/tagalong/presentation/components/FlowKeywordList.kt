package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun <T> FlowKeywordList( // TODO: Make TextField and/or Keywords customizable?
    keywordObjects: List<T>,
    onClickDeleteIcon: ((T) -> Unit)? = null,
    onAddNewKeyword: ((String) -> Unit)? = null
) {
    val newKeyword = mutableStateOf("")
    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            FlowRow( // TODO: Make scrollable? ALSO: Explore nested scrolling
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
            onAddNewKeyword?.let {
                TextField(
                    value = newKeyword.value,
                    onValueChange = { newKeyword.value = it },
                    label = { Text("Add a tag here...") }, // TODO: Make text customizable
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onAddNewKeyword(newKeyword.value)
                            newKeyword.value = ""
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Tag, contentDescription = "Add tag") }, // TODO: Make text customizable?
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}