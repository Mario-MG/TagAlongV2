package com.hfad.tagalong.presentation.ui.playlist_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.ItemCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistListFragment : Fragment() {

    private val viewModel: PlaylistListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val playlists = viewModel.playlistList.value

                AppTheme {
                    LazyColumn {
                        itemsIndexed(items = playlists) { index, playlist ->
                            ItemCard(
                                imageUrl = playlist.imageUrl,
                                title = playlist.name,
                                subtitle = "${playlist.size} songs") // TODO: Handle singular/plural
                        }
                    }
                }
            }
        }
    }
}