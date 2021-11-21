package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST_ID
import com.hfad.tagalong.presentation.ui.tracks.PlaylistTracksEvent.FirstPageEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistTracksFragment : TracksFragment(viewTrackAction = R.id.viewTrackFromPlaylist) {

    override val viewModel: PlaylistTracksViewModel by viewModels()

    private lateinit var playlistId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(BUNDLE_KEY_PLAYLIST_ID)?.let { playlistId ->
            this.playlistId = playlistId
            viewModel.onTriggerEvent(FirstPageEvent(playlistId))
        }
    }

    override fun onTriggerNextPage() {
        super.onTriggerNextPage()
        viewModel.onTriggerEvent(PlaylistTracksEvent.NextPageEvent(playlistId))
    }
}