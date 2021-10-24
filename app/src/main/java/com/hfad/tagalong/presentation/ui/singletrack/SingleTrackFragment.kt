package com.hfad.tagalong.presentation.ui.singletrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.util.DEFAULT_ALBUM_IMAGE
import com.hfad.tagalong.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SingleTrackFragment : Fragment() {

    private val viewModel: SingleTrackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("trackId")?.let { trackId -> // TODO: Extract to constant
            viewModel.loadTrack(trackId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val track = viewModel.track.value

                AppTheme {
                    track?.let {
                        Surface {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val image = loadPicture(
                                    url = track.imageUrl,
                                    defaultImage = DEFAULT_ALBUM_IMAGE
                                ).value
                                val imageSize = 200.dp
                                image?.let { img ->
                                    Image(
                                        bitmap = img.asImageBitmap(),
                                        contentDescription = "Item Image",
                                        modifier = Modifier
                                            .height(imageSize)
                                            .width(imageSize),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = track.name,
                                    style = MaterialTheme.typography.h2
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${track.album} | ${track.artists[0]}",
                                    style = MaterialTheme.typography.h5.copy(
                                        color = MaterialTheme.colors.onSurface.copy(0.5f)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}