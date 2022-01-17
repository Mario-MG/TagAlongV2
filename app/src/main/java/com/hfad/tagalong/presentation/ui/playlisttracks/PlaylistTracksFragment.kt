package com.hfad.tagalong.presentation.ui.playlisttracks

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.fragment.app.viewModels
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST
import com.hfad.tagalong.presentation.adapters.PlaylistParcelable
import com.hfad.tagalong.presentation.ui.playlisttracks.PlaylistTracksEvent.InitPlaylistTracksEvent
import com.hfad.tagalong.presentation.ui.playlisttracks.PlaylistTracksEvent.NextPageEvent
import com.hfad.tagalong.presentation.ui.tracks.TracksFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistTracksFragment : TracksFragment() {

    override val viewModel: PlaylistTracksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<PlaylistParcelable>(BUNDLE_KEY_PLAYLIST)?.let { playlistParcelable ->
            viewModel.onTriggerEvent(InitPlaylistTracksEvent(playlistParcelable.playlist))
        }
    }

    override fun onTriggerNextPage() {
        super.onTriggerNextPage()
        viewModel.onTriggerEvent(NextPageEvent)
    }
}