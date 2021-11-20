package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun EmptyListPlaceholderText(
    text: String
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (textRef) = createRefs()
        val topBias = createGuidelineFromTop(0.08f)
        Text(
            text = text,
            style = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.onBackground.copy(0.5f),
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier.constrainAs(textRef)
            {
                top.linkTo(topBias)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        )
    }
}