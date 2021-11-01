package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST_ID
import com.hfad.tagalong.presentation.BUNDLE_KEY_TRACK_ID
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.components.TrackItemCard
import com.hfad.tagalong.presentation.components.TrackItemList
import com.hfad.tagalong.presentation.ui.tracks.TracksEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.tracks.TracksEvent.NextPageEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TracksFragment : Fragment() {

    private val viewModel: TracksViewModel by viewModels()

    private lateinit var playlistId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(BUNDLE_KEY_PLAYLIST_ID)?.let { playlistId ->
            this.playlistId = playlistId
            viewModel.onTriggerEvent(FirstPageEvent(playlistId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val tracks = viewModel.tracks
                val loading = viewModel.loading.value

                AppTheme(
                    displayProgressBar = loading,
                    progressBarAlignment = if (tracks.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                ) {
                    TrackItemList(
                        tracks = tracks,
                        loading = loading,
                        onTriggerNextPage = {
                            viewModel.onTriggerEvent(NextPageEvent(playlistId))
                        },
                        onNavigateToTrackDetail = { track ->
                            navigateToTrackDetail(track)
                        }
                    )
                }
            }
        }
    }

    private fun navigateToTrackDetail(track: Track) {
        val bundle = Bundle().also { it.putString(BUNDLE_KEY_TRACK_ID, track.id) }
        findNavController().navigate(R.id.viewTrack, bundle)
    }
}