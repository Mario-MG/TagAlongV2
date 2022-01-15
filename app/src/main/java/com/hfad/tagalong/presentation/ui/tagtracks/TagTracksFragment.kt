package com.hfad.tagalong.presentation.ui.tagtracks

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.fragment.app.viewModels
import com.hfad.tagalong.presentation.BUNDLE_KEY_TAG
import com.hfad.tagalong.presentation.adapters.TagParcelable
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.InitTagTracksEvent
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.LoadTagTracksEvent
import com.hfad.tagalong.presentation.ui.tracks.TracksFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TagTracksFragment : TracksFragment() {

    override val viewModel: TagTracksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<TagParcelable>(BUNDLE_KEY_TAG)?.let { tagAdapter ->
            viewModel.onTriggerEvent(InitTagTracksEvent(tagAdapter.tag))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(LoadTagTracksEvent)
    }
}