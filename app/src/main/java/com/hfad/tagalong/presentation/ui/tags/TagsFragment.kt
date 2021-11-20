package com.hfad.tagalong.presentation.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.BUNDLE_KEY_TAG_ID
import com.hfad.tagalong.presentation.components.TagItemList
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.tags.TagsEvent.LoadTagsEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TagsFragment : Fragment() {

    private val viewModel: TagsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val tags = viewModel.tags
                val loading = viewModel.loading.value

                AppTheme(
                    displayProgressBar = loading,
                    progressBarAlignment = if (tags.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            ) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.QueueMusic, contentDescription = "Playlists icon") },
                                    label = { Text("Playlists") },
                                    selected = false,
                                    onClick = { findNavController().navigate(R.id.tags_to_playlists) },
                                    colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colors.primaryVariant)
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Tag, contentDescription = "Tags icon") },
                                    label = { Text("Tags") },
                                    selected = true,
                                    onClick = {},
                                    colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colors.primaryVariant)
                                )
                            }
                        }
                    ) {
                        TagItemList(
                            tags = tags,
                            onNavigateToTrackList = { tag ->
                                navigateToTrackList(tag)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(LoadTagsEvent)
    }

    private fun navigateToTrackList(tag: Tag) {
        val bundle = bundleOf(BUNDLE_KEY_TAG_ID to tag.id)
        findNavController().navigate(R.id.viewTagTracks, bundle)
    }
}