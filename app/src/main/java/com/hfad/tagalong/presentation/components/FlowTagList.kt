package com.hfad.tagalong.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.adapters.TagKeyword
import com.hfad.tagalong.tag_domain.Tag

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun FlowTagList(
    tags: List<Tag>,
    onClickDeleteIcon: ((Tag) -> Unit)? = null,
    textFieldLabel: String = stringResource(R.string.add_tag_here),
    textFieldLeadingIcon: @Composable (() -> Unit)? = {
        Icon(Icons.Filled.Tag, contentDescription = stringResource(R.string.tag_icon_description))
    },
    onAddNewKeyword: ((String) -> Unit)? = null,
    newKeywordValidation: (String) -> Boolean = { true },
    predictions: List<Tag> = emptyList(),
    predictionFilter: (Tag, String) -> Boolean = { _, _ -> true }
) {
    FlowKeywordList(
        keywordObjects = tags.map(::TagKeyword),
        onClickDeleteIcon = onClickDeleteIcon?.let{{ tagKeyword -> onClickDeleteIcon(tagKeyword.tag) }},
        textFieldLabel = textFieldLabel,
        textFieldLeadingIcon = textFieldLeadingIcon,
        onAddNewKeyword = onAddNewKeyword,
        newKeywordValidation = newKeywordValidation,
        predictions = predictions.map(::TagKeyword),
        predictionFilter = { tagKeyword, currentKeyword -> predictionFilter(tagKeyword.tag, currentKeyword) }
    )
}