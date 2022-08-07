package com.mariomg.tagalong.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariomg.tagalong.R
import com.mariomg.tagalong.presentation.components.*
import com.mariomg.tagalong.presentation.util.DialogData
import com.mariomg.tagalong.presentation.util.DialogData.ErrorDialog
import com.mariomg.tagalong.presentation.util.DialogData.InfoDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

class AppScaffold(
    private val navController: NavController,
    private val scaffoldState: ScaffoldState? = null
) {

    private var displayProgressBar = false
    private var progressBarAlignment = Alignment.TopCenter

    private var displayNavBar = false

    private var displayTopBar = false
    private var screenTitle: String? = null
    private var pinnedTopBar = false
    private var showBackButton = false
    private var helpContent: @Composable (() -> Unit)? = null

    private var floatingActionButton: @Composable () -> Unit = {}

    private var currentDialog: DialogData? = null

    fun withProgressBar(
        displayProgressBar: Boolean,
        progressBarAlignment: Alignment = Alignment.TopCenter
    ): AppScaffold {
        this.displayProgressBar = displayProgressBar
        this.progressBarAlignment = progressBarAlignment
        return this
    }

    fun withNavBar(): AppScaffold {
        this.displayNavBar = true
        return this
    }

    fun withTopBar(
        screenTitle: String? = null,
        pinned: Boolean = false,
        showBackButton: Boolean = false,
        helpContent: @Composable (() -> Unit)? = null
    ): AppScaffold {
        this.displayTopBar = true
        this.screenTitle = screenTitle
        this.pinnedTopBar = pinned
        this.showBackButton = showBackButton
        this.helpContent = helpContent
        return this
    }

    fun withFloatingActionButton(
        floatingActionButton: @Composable () -> Unit
    ): AppScaffold {
        this.floatingActionButton = floatingActionButton
        return this
    }

    fun withDialog(
        currentDialog: DialogData?
    ): AppScaffold {
        this.currentDialog = currentDialog
        return this
    }

    @SuppressLint("ComposableNaming")
    @ExperimentalMaterial3Api
    @Composable
    fun setContent(
        content: @Composable (BoxScope.() -> Unit)
    ) = AppScaffold(
        scaffoldState = this.scaffoldState ?: rememberScaffoldState(),
        navController = this.navController,
        displayProgressBar = this.displayProgressBar,
        progressBarAlignment = this.progressBarAlignment,
        displayNavBar = this.displayNavBar,
        displayTopBar = this.displayTopBar,
        screenTitle = this.screenTitle ?: stringResource(R.string.app_name),
        pinnedTopBar = this.pinnedTopBar,
        showBackButtonInTopBar = this.showBackButton,
        helpContent = this.helpContent,
        floatingActionButton = this.floatingActionButton,
        currentDialog = this.currentDialog
    ) {
        content()
    }

}

@ExperimentalMaterial3Api
@Composable
private fun AppScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController,
    displayProgressBar: Boolean = false,
    progressBarAlignment: Alignment = Alignment.TopCenter,
    displayNavBar: Boolean = false,
    displayTopBar: Boolean = true,
    screenTitle: String = stringResource(R.string.app_name),
    pinnedTopBar: Boolean = false,
    showBackButtonInTopBar: Boolean = false,
    helpContent: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    currentDialog: DialogData? = null,
    content: @Composable (BoxScope.() -> Unit)
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
                if (displayTopBar) {
                    AppTopBar(
                        title = screenTitle,
                        navController = navController,
                        showBackButton = showBackButtonInTopBar,
                        scrollBehavior = scrollBehavior,
                        onClickHelp = onClickHelp
                    )
                }
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
                DefaultSnackbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    snackbarHostState = scaffoldState.snackbarHostState
                )
                ProcessDialog(
                    currentDialog = currentDialog,
                    snackbarHostState = scaffoldState.snackbarHostState
                )
            }
        }
    }
}

@Composable
private fun ProcessDialog(
    currentDialog: DialogData?,
    snackbarHostState: SnackbarHostState
) {
    currentDialog?.let { dialog ->
        when (dialog) {
            is InfoDialog -> showInfoSnackbar(
                message = dialog.description,
                actionLabel = stringResource(R.string.snackbar_ok),
                snackbarHostState = snackbarHostState,
                scope = rememberCoroutineScope(),
                onDismiss = dialog.onDismiss
            )
            is ErrorDialog -> GenericErrorDialog(dialog)
        }
    }
}

private fun showInfoSnackbar(
    message: String,
    actionLabel: String? = null,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onDismiss: () -> Unit
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel
        )
        onDismiss()
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
