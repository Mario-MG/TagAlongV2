package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Source: https://github.com/mitchtabian/MVVMRecipeApp/blob/master/app/src/main/java/com/codingwithmitch/mvvmrecipeapp/presentation/components/DefaultSnackbar.kt

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                snackbarData = data,
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(24.dp)
            )
        },
        modifier = modifier
    )
}








