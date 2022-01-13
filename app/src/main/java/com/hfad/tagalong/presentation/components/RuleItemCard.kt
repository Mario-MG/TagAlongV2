package com.hfad.tagalong.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Rule

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun RuleItemCard(
    rule: Rule,
    onClick: () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 8.dp
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = rule.playlist.name,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Icon(
                    imageVector = Icons.Default.Autorenew,
                    contentDescription = stringResource(R.string.autoupdate_icon_description),
                    tint = if (rule.autoUpdate) Color.Green else MaterialTheme.colors.onSurface.copy(0.2f)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = buildAnnotatedString {
                    if (rule.optionality) {
                        append(stringResource(R.string.before_rule_optionality_any))
                        withStyle(style = SpanStyle(
                            color = MaterialTheme.colors.onSurface.copy(0.8f),
                            fontWeight = FontWeight.Medium
                        )) {
                            append(stringResource(R.string.rule_optionality_any))
                        }
                        append(stringResource(R.string.after_rule_optionality_any))
                    } else {
                        append(stringResource(R.string.before_rule_optionality_all))
                        withStyle(style = SpanStyle(
                            color = MaterialTheme.colors.onSurface.copy(0.8f),
                            fontWeight = FontWeight.Medium
                        )) {
                            append(stringResource(R.string.rule_optionality_all))
                        }
                        append(stringResource(R.string.after_rule_optionality_all))
                    }
                },
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onSurface.copy(0.5f)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowTagList(
                tags = rule.tags
            )
        }
    }
}