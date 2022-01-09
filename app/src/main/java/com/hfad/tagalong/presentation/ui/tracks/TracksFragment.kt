package com.hfad.tagalong.presentation.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.BUNDLE_KEY_TRACK
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.components.TrackItemList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
abstract class TracksFragment : BaseLoggedInFragment() {

    protected abstract val viewModel: TracksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val tracks = viewModel.tracks
                val loading = viewModel.loading
                val screenTitle = viewModel.screenTitle

                val dialogQueue = viewModel.dialogQueue

                AppScaffold(navController = findNavController())
                    .withProgressBar(
                        displayProgressBar = loading,
                        progressBarAlignment = if (tracks.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                    )
                    .withTopBar(
                        screenTitle = screenTitle,
                        showBackButton = true,
                        helpContent = { Text(stringResource(R.string.tracks_help)) }
                    )
                    .withDialog(dialogQueue.currentDialog)
                    .setContent {
                        if (tracks.isNotEmpty()) {
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
                        } else if (!loading) {
                            EmptyListPlaceholderText(text = stringResource(R.string.no_tracks))
                        }
                    }
            }
        }
    }

    protected open fun onTriggerNextPage() {}

    private fun navigateToTrackDetail(track: Track) {
        val bundle = bundleOf(BUNDLE_KEY_TRACK to track)
        findNavController().navigate(
            R.id.singleTrackFragment,
            bundle,
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        )
    }
}