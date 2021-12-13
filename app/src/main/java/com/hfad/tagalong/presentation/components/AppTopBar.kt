package com.hfad.tagalong.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.hfad.tagalong.R

@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    SmallTopAppBar(
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back_arrow_description))
                }
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // TODO: This is not recommended by MaterialDesign
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colors.surface,
            titleContentColor = MaterialTheme.colors.onSurface,
            navigationIconContentColor = MaterialTheme.colors.onSurface
        ),
        scrollBehavior = scrollBehavior
    )
}