package com.hfad.tagalong.presentation.ui.singletrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_TRACK
import com.hfad.tagalong.presentation.adapters.TrackParcelable
import com.hfad.tagalong.presentation.components.FlowTagList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import com.hfad.tagalong.presentation.ui.singletrack.SingleTrackEvent.*
import com.hfad.tagalong.presentation.util.DEFAULT_ALBUM_IMAGE
import com.hfad.tagalong.presentation.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SingleTrackFragment : BaseLoggedInFragment() {

    private val viewModel: SingleTrackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<TrackParcelable>(BUNDLE_KEY_TRACK)?.let { trackParcelable ->
            viewModel.onTriggerEvent(InitTrackEvent(trackParcelable.track))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val track = viewModel.track
                val tags = viewModel.tagsForTrack
                val loading = viewModel.loading
                val allTags = viewModel.allTags

                val dialogQueue = viewModel.dialogQueue

                AppScaffold(navController = findNavController())
                    .withProgressBar(displayProgressBar = loading)
                    .withTopBar(
                        screenTitle = track!!.name,
                        pinned = true,
                        showBackButton = true,
                        helpContent = { Text(stringResource(R.string.single_track_help)) }
                    )
                    .withDialog(dialogQueue.currentDialog)
                    .setContent {
                        Surface(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))
                                val image = loadPicture(
                                    url = track.imageUrl,
                                    defaultImage = DEFAULT_ALBUM_IMAGE
                                ).value
                                val imageSize = 200.dp
                                image?.let { img ->
                                    Image(
                                        bitmap = img.asImageBitmap(),
                                        contentDescription = stringResource(R.string.track_image_description),
                                        modifier = Modifier
                                            .height(imageSize)
                                            .width(imageSize),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = track.name,
                                    style = MaterialTheme.typography.h2,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${track.album} | ${track.artists[0]}",
                                    style = MaterialTheme.typography.h5.copy(
                                        color = MaterialTheme.colors.onSurface.copy(0.5f)
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                FlowTagList(
                                    tags = tags,
                                    onAddNewKeyword = { tagName ->
                                        viewModel.onTriggerEvent(AddTagEvent(tagName))
                                    },
                                    onClickDeleteIcon = { tag ->
                                        viewModel.onTriggerEvent(DeleteTagEvent(tag))
                                    },
                                    predictions = allTags,
                                    predictionFilter = { tag, currentValue ->
                                        !tags.contains(tag) && tag.name.contains(currentValue)
                                    }
                                )
                            }
                        }
                    }
            }
        }
    }
}