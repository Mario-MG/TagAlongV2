package com.mariomg.tagalong.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.mariomg.tagalong.rule_domain.Rule

@ExperimentalAnimationApi
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