package com.hfad.tagalong.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.ItemCard
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.settings.SettingsEvent.LogOutEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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
                val navController = findNavController()

                AppScaffold(
                    navController = navController
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        ItemCard(
                            title = "Log out",
                            onClick = {
                                viewModel.onTriggerEvent(LogOutEvent {
                                    findNavController().navigate(R.id.logOut)
                                })
                            },
                            cardHeight = 60.dp
                        )
                    }
                }
            }
        }
    }
}