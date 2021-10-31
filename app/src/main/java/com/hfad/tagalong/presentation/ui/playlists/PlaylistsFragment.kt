package com.hfad.tagalong.presentation.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.components.PlaylistItemCard
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
                val playlists = viewModel.playlistList
                val loading = viewModel.loading.value

                AppTheme {
                    LazyColumn {
                        itemsIndexed(items = playlists) { index, playlist ->
                            if (index + 1 >= playlists.size && !loading) {
                                viewModel.onTriggerEvent(NextPageEvent)
                            }
                            PlaylistItemCard(
                                playlist = playlist,
                                onClick = {
                                    goToTrackList(playlist)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun goToTrackList(playlist: Playlist) {
        val bundle = Bundle().also { it.putString("playlistId", playlist.id) }
        findNavController().navigate(R.id.viewTracks, bundle)
    }
}