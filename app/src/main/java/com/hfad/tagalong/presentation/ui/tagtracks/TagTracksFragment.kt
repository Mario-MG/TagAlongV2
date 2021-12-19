package com.hfad.tagalong.presentation.ui.tagtracks

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.fragment.app.viewModels
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.BUNDLE_KEY_TAG
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.InitTagTracksEvent
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.ReloadTagTracksEvent
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
        arguments?.getParcelable<Tag>(BUNDLE_KEY_TAG)?.let { tag ->
            viewModel.onTriggerEvent(InitTagTracksEvent(tag))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(ReloadTagTracksEvent)
    }
}