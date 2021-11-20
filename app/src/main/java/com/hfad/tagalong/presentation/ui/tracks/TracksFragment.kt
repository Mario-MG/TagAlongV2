package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.BUNDLE_KEY_TRACK_ID
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.components.TrackItemList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class TracksFragment(
    @IdRes val viewTrackAction: Int
) : Fragment() {

    protected abstract val viewModel: TracksViewModel

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
                            onTriggerNextPage()
                        },
                        onNavigateToTrackDetail = { track ->
                            navigateToTrackDetail(track)
                        }
                    )
                }
            }
        }
    }

    protected open fun onTriggerNextPage() {}

    private fun navigateToTrackDetail(track: Track) {
        val bundle = bundleOf(BUNDLE_KEY_TRACK_ID to track.id)
        findNavController().navigate(viewTrackAction, bundle)
    }
}