package com.hfad.tagalong.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.hfad.tagalong.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun LoginButton(
    @DrawableRes iconDrawable: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(48.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 8.dp
                )
        ) {
            val icon = loadPicture(iconDrawable).value
            icon?.let {
                Icon(
                    bitmap = icon.asImageBitmap(),
                    contentDescription = "Login button icon",
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically),
                    tint = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}