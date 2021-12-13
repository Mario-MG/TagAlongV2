package com.hfad.tagalong.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.darkColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.AppNavigationBar
import com.hfad.tagalong.presentation.components.AppTopBar

@Composable
fun AppTheme(
    content: @Composable BoxScope.() -> Unit
) {
    MaterialTheme(
        colors = DarkThemeColours,
        typography = AppTypography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            content()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun AppScaffold(
    displayProgressBar: Boolean = false,
    progressBarAlignment: Alignment = Alignment.TopCenter,
    displayNavBar: Boolean = false,
    navController: NavController,
    floatingActionButton: @Composable () -> Unit = {},
    screenTitle: String = stringResource(R.string.app_name),
    showBackButtonInTopBar: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    AppTheme {
        val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                AppTopBar(
                    title = screenTitle,
                    navController = navController,
                    showBackButton = showBackButtonInTopBar,
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                if (displayNavBar) {
                    AppNavigationBar(
                        navController = navController
                    )
                }
            },
            floatingActionButton = floatingActionButton
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                content()
                if (displayProgressBar) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(progressBarAlignment)
                            .padding(12.dp)
                    )
                }
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
