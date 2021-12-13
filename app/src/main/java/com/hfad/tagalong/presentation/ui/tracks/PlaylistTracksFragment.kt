package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.fragment.app.viewModels
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.BUNDLE_KEY_PLAYLIST
import com.hfad.tagalong.presentation.ui.tracks.PlaylistTracksEvent.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlaylistTracksFragment : TracksFragment() {

    override val viewModel: PlaylistTracksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Playlist>(BUNDLE_KEY_PLAYLIST)?.let { playlist ->
            viewModel.onTriggerEvent(InitPlaylistTracksEvent(playlist))
        }
    }

    override fun onTriggerNextPage() {
        super.onTriggerNextPage()
        viewModel.onTriggerEvent(NextPageEvent)
    }
}