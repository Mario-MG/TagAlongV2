package com.hfad.tagalong.presentation.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppTheme(
    displayProgressBar: Boolean = false,
    progressBarAlignment: Alignment = Alignment.TopCenter,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkThemeColours,
        typography = AppTypography
    ) {
        Box {
            content()
            if (displayProgressBar) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(progressBarAlignment)
                        .padding(
                            bottom = 96.dp,
                            top = 12.dp
                        )
                )
            }
        }
    }
}

val DarkThemeColours = darkColors(
    primary = SpotifyGreen500,
    primaryVariant = SpotifyGreen300,
    onPrimary = Color.White,
    secondary = SpotifyAnalogousGreen500,
    onSecondary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = SpotifyBlack900,
    onSurface = Color.White
)
