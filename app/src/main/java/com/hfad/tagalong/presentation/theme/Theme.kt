package com.hfad.tagalong.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.AppNavigationBar
import com.hfad.tagalong.presentation.components.AppTopBar
import com.hfad.tagalong.presentation.components.HelpDialog

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
    pinnedTopBar: Boolean = false,
    showBackButtonInTopBar: Boolean = false,
    helpContent: @Composable (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    AppTheme {
        val scrollBehavior = remember {
            if (pinnedTopBar) TopAppBarDefaults.pinnedScrollBehavior()
            else TopAppBarDefaults.enterAlwaysScrollBehavior()
        }
        var showHelpDialog by remember { mutableStateOf(false) }
        val onClickHelp = helpContent?.let { { showHelpDialog = true } }
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                AppTopBar(
                    title = screenTitle,
                    navController = navController,
                    showBackButton = showBackButtonInTopBar,
                    scrollBehavior = scrollBehavior,
                    onClickHelp = onClickHelp
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
                if (showHelpDialog) {
                    HelpDialog(onDismissRequest = { showHelpDialog = false }) {
                        helpContent!!()
                    }
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
