package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun <T> FlowKeywordList( // TODO: Make TextField and/or Keywords customizable (appearance-wise)?
    keywordObjects: List<T>,
    onClickDeleteIcon: ((T) -> Unit)? = null,
    textFieldLabel: @Composable (() -> Unit)? = { Text("Add a keyword here...") },
    textFieldLeadingIcon: @Composable (() -> Unit)? = null,
    onAddNewKeyword: ((String) -> String)? = null
) {
    val newKeyword = remember { mutableStateOf("") }

    // Source: https://issuetracker.google.com/issues/192043120#comment17 (see comment #19 too)
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

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
            FlowRow(
                modifier = Modifier
                    .padding(8.dp)
//                    .verticalScroll(rememberScrollState()) // FIXME: Crashes the app
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
                    onValueChange = {
                        newKeyword.value = it
                        refocus(bringIntoViewRequester = bringIntoViewRequester, scope = scope)
                    },
                    label = textFieldLabel,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            newKeyword.value = onAddNewKeyword(newKeyword.value)
                            refocus(bringIntoViewRequester = bringIntoViewRequester, scope = scope)
                        },
                    ),
                    leadingIcon = textFieldLeadingIcon,
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusChanged {
                            if ("$it" == "Active") {
                                refocus(bringIntoViewRequester = bringIntoViewRequester, scope = scope)
                            }
                        }
                )
            }
        }
    }
}

@ExperimentalFoundationApi
private fun refocus(bringIntoViewRequester: BringIntoViewRequester, scope: CoroutineScope) {
    scope.launch {
        delay(500)
        bringIntoViewRequester.bringIntoView()
    }
}