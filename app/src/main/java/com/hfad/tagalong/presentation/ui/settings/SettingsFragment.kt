package com.hfad.tagalong.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.presentation.components.AppNavigationBar
import com.hfad.tagalong.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme {
                    Scaffold(
                        bottomBar = {
                            AppNavigationBar(
                                navController = findNavController()
                            )
                        }
                    ) {
                    }
                }
            }
        }
    }
}