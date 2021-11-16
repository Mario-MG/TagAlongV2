package com.hfad.tagalong.presentation.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST_ID
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
                            NavigationBar(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            ) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.QueueMusic, contentDescription = "Playlists icon") },
                                    label = { Text("Playlists") },
                                    selected = true,
                                    onClick = {},
                                    colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colors.primaryVariant)
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Tag, contentDescription = "Tags icon") },
                                    label = { Text("Tags") },
                                    selected = false,
                                    onClick = { findNavController().navigate(R.id.playlists_to_tags) }
                                )
                            }
                        }
                    ) {
                        PlaylistItemList(
                            playlists = playlists,
                            loading = loading,
                            onTriggerNextPage =  {
                                viewModel.onTriggerEvent(NextPageEvent)
                            },
                            onNavigateToTrackList = { playlist ->
                                navigateToTrackList(playlist)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun navigateToTrackList(playlist: Playlist) {
        val bundle = bundleOf(BUNDLE_KEY_PLAYLIST_ID to playlist.id)
        findNavController().navigate(R.id.viewTracks, bundle)
    }
}