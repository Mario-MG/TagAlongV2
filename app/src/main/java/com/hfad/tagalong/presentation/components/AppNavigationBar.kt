package com.hfad.tagalong.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.ui.Screen

@Composable
fun AppNavigationBar(
    navController: NavController
) {
    NavigationBar(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val items = listOf(
            Screen.Playlists,
            Screen.Tags,
            Screen.Rules,
            Screen.Settings
        )
        val navBackStackEntry = navController.currentBackStackEntry
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val selected = screen.route == currentDestination?.id
            NavigationBarItem(
                icon = screen.icon,
                label = { Text(screen.label) },
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(
                            screen.route,
                            null,
                            NavOptions.Builder() // FIXME: NavigationBar visibly "pulsates" when changing tabs
                                .setPopUpTo(items[0].route, false)
                                .setLaunchSingleTop(true)
                                .setEnterAnim(R.anim.fade_in)
                                .setExitAnim(R.anim.fade_out)
                                .setPopEnterAnim(R.anim.fade_in)
                                .setPopExitAnim(R.anim.fade_out)
                                .build()
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colors.primaryVariant
                )
            )
        }
    }
}