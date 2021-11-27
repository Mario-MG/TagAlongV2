package com.hfad.tagalong.presentation.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST_ID
import com.hfad.tagalong.presentation.components.AppNavigationBar
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.components.PlaylistItemList
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.NextPageEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistsFragment : Fragment() {

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

                AppTheme(
                    displayProgressBar = loading,
                    progressBarAlignment = if (playlists.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                ) {
                    Scaffold(
                        bottomBar = {
                            AppNavigationBar(
                                navController = findNavController()
                            )
                        }
                    ) {
                        if (playlists.isNotEmpty()) {
                            PlaylistItemList(
                                playlists = playlists,
                                loading = loading,
                                onTriggerNextPage = {
                                    viewModel.onTriggerEvent(NextPageEvent)
                                },
                                onNavigateToTrackList = { playlist ->
                                    navigateToTrackList(playlist)
                                }
                            )
                        } else if (!loading) {
                            EmptyListPlaceholderText(text = "There are no playlists to show")
                        }
                    }
                }
            }
        }
    }

    private fun navigateToTrackList(playlist: Playlist) {
        val bundle = bundleOf(BUNDLE_KEY_PLAYLIST_ID to playlist.id)
        findNavController().navigate(R.id.viewPlaylistTracks, bundle)
    }
}