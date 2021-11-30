package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Rule

@ExperimentalFoundationApi
@Composable
fun RuleItemList(
    rules: List<Rule>,
    onClickRuleItem: (Rule) -> Unit
) {
    LazyColumn {
        items(items = rules) { rule ->
            RuleItemCard(
                rule = rule,
                onClick = {
                    onClickRuleItem(rule)
                }
            )
        }
    }
}