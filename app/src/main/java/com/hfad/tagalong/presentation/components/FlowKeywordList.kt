package com.hfad.tagalong.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.hfad.tagalong.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun <T : Keyword> FlowKeywordList( // TODO: Make TextField and/or Keywords customizable (appearance-wise)?
    keywordObjects: List<T>,
    onClickDeleteIcon: ((T) -> Unit)? = null,
    textFieldLabel: String = stringResource(R.string.add_keyword_here),
    textFieldLeadingIcon: @Composable (() -> Unit)? = null,
    onAddNewKeyword: ((String) -> Unit)? = null,
    newKeywordValidation: (String) -> Boolean = { true },
    predictions: List<T> = emptyList(),
    predictionFilter: (T, String) -> Boolean = { _, _ -> true }
) {
    var currentKeyword by remember { mutableStateOf("") }

    // Source: https://issuetracker.google.com/issues/192043120#comment17 (see comment #19 too)
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    val view = LocalView.current

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
                val onNewKeyword = { newKeyword: String ->
                    if (newKeywordValidation(newKeyword)) {
                        onAddNewKeyword(newKeyword)
                        currentKeyword = ""
                        view.clearFocus()
                    }
                }
                AutoCompleteTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusChanged {
                            if ("$it" == "Active") {
                                refocus(
                                    bringIntoViewRequester = bringIntoViewRequester,
                                    scope = scope
                                )
                            }
                        },
                    query = currentKeyword,
                    queryLabel = textFieldLabel,
                    leadingIcon = textFieldLeadingIcon,
                    onQueryChange = {
                        currentKeyword = it
                        refocus(bringIntoViewRequester = bringIntoViewRequester, scope = scope)
                    },
                    predictions = predictions,
                    predictionFilter = { keyword -> predictionFilter(keyword, currentKeyword) },
                    onDoneActionClick = {
                        onNewKeyword(currentKeyword)
                        refocus(bringIntoViewRequester = bringIntoViewRequester, scope = scope)
                    },
                    onClearClick = {
                        currentKeyword = ""
                    },
                    onItemClick = { clickedKeyword ->
                        onNewKeyword(clickedKeyword.value())
                    },
                    itemContent = {
                        Keyword(keywordObject = it)
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