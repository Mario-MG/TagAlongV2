package com.hfad.tagalong.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.ItemCard
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.Screen
import com.hfad.tagalong.presentation.ui.main.MainViewModel
import com.hfad.tagalong.presentation.ui.settings.SettingsEvent.LogOutEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val dialogQueue = viewModel.dialogQueue

                AppScaffold(navController = findNavController())
                    .withNavBar()
                    .withTopBar(
                        screenTitle = Screen.Settings.getLabel(),
                        pinned = true
                    )
                    .withDialog(dialogQueue.currentDialog.value)
                    .setContent {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            ItemCard(
                                title = stringResource(R.string.log_out),
                                onClick = {
                                    viewModel.onTriggerEvent(LogOutEvent)
                                },
                                cardHeight = 60.dp
                            )
                        }
                    }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.addLogoutObserver(viewLifecycleOwner, {
            findNavController().navigate(
                R.id.playlistsFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.settingsFragment, true)
                    .setLaunchSingleTop(true)
                    .setEnterAnim(R.anim.slide_in_up)
                    .setPopExitAnim(R.anim.slide_out_up)
                    .build()
            )
        })
    }

}