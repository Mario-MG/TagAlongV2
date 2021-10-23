package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.ItemCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TracksFragment : Fragment() {

    private val viewModel: TracksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("playlistId")?.let { playlistId -> // TODO: Extract to constant
            viewModel.loadTracks(playlistId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val tracks = viewModel.tracks.value

                AppTheme {
                    LazyColumn {
                        itemsIndexed(items = tracks) { index, track ->
                            ItemCard(
                                imageUrl = track.imageUrl,
                                title = track.name,
                                subtitle = "${track.album} | ${track.artists[0]}") // TODO: Handle multiple artists / no artists?
                        }
                    }
                }
            }
        }
    }
}