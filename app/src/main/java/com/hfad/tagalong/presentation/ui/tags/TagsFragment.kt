package com.hfad.tagalong.presentation.ui.tags

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_TAG
import com.hfad.tagalong.presentation.adapters.TagAdapter
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.components.TagItemList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import com.hfad.tagalong.presentation.ui.Screen
import com.hfad.tagalong.presentation.ui.tags.TagsEvent.LoadTagsEvent
import com.hfad.tagalong.tag_domain.Tag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TagsFragment : BaseLoggedInFragment() {

    private val viewModel: TagsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val tags = viewModel.tags
                val loading = viewModel.loading

                val dialogQueue = viewModel.dialogQueue

                AppScaffold(navController = findNavController())
                    .withProgressBar(
                        displayProgressBar = loading,
                        progressBarAlignment = if (tags.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                    )
                    .withNavBar()
                    .withTopBar(
                        screenTitle = Screen.Tags.getLabel(),
                        helpContent = { Text(stringResource(R.string.tags_help)) }
                    )
                    .withDialog(dialogQueue.currentDialog)
                    .setContent {
                        if (tags.isNotEmpty()) {
                            TagItemList(
                                tags = tags,
                                onClickTagItem = { tag ->
                                    navigateToTrackList(tag)
                                }
                            )
                        } else if (!loading) {
                            EmptyListPlaceholderText(text = stringResource(R.string.no_tags))
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
        val bundle = bundleOf(BUNDLE_KEY_TAG to TagAdapter(tag))
        findNavController().navigate(R.id.viewTagTracks, bundle)
    }
}