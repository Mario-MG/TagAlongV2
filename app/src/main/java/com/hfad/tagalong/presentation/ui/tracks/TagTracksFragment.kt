package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_TAG_ID
import com.hfad.tagalong.presentation.ui.tracks.TagTracksEvent.LoadTagTracksEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TagTracksFragment : TracksFragment(viewTrackAction = R.id.viewTrackFromTag) {

    override val viewModel: TagTracksViewModel by viewModels()

    private var tagId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong(BUNDLE_KEY_TAG_ID)?.let { tagId ->
            this.tagId = tagId
            viewModel.onTriggerEvent(LoadTagTracksEvent(tagId))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(LoadTagTracksEvent(tagId))
    }
}