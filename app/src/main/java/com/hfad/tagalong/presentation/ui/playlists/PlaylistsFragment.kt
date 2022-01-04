package com.hfad.tagalong.presentation.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST
import com.hfad.tagalong.presentation.LOGIN_SUCCESSFUL
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.components.PlaylistItemList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import com.hfad.tagalong.presentation.ui.Screen
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.NextPageEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistsFragment : BaseLoggedInFragment() {

    private val viewModel: PlaylistsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val playlists = viewModel.playlists
                val loading = viewModel.loading.value
                val isLoggedIn = mainViewModel.isLoggedIn.value
                val dialogQueue = viewModel.dialogQueue

                if (isLoggedIn) {
                    AppScaffold(navController = findNavController())
                        .withNavBar()
                        .withTopBar(
                            screenTitle = Screen.Playlists.getLabel(),
                            helpContent = { Text(stringResource(R.string.playlists_help)) }
                        )
                        .withProgressBar(
                            displayProgressBar = loading,
                            progressBarAlignment = if (playlists.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                        )
                        .withDialog(currentDialog = dialogQueue.currentDialog.value)
                        .setContent {
                            if (playlists.isNotEmpty()) {
                                PlaylistItemList(
                                    playlists = playlists,
                                    loading = loading,
                                    onTriggerNextPage = {
                                        viewModel.onTriggerEvent(NextPageEvent)
                                    },
                                    onClickPlaylistItem = { playlist ->
                                        navigateToTrackList(playlist)
                                    }
                                )
                            } else if (!loading) {
                                EmptyListPlaceholderText(text = stringResource(R.string.no_playlists))
                            }
                        }
                }
            }
        }
    }

    private fun navigateToTrackList(playlist: Playlist) {
        val bundle = bundleOf(BUNDLE_KEY_PLAYLIST to playlist)
        findNavController().navigate(R.id.viewPlaylistTracks, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.addLoginSuccessObserver(viewLifecycleOwner) {
            viewModel.onTriggerEvent(FirstPageEvent)
        }

        // Source: https://youtu.be/09qjn706ITA?t=284
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
            ?: throw IllegalStateException()
        savedStateHandle.getLiveData<Boolean>(LOGIN_SUCCESSFUL)
            .observe(viewLifecycleOwner) { success ->
                if (!success) {
                    requireActivity().finish()
                }
            }
    }

}