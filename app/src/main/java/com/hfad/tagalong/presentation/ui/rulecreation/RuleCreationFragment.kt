package com.hfad.tagalong.presentation.ui.rulecreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.FlowKeywordList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import com.hfad.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class RuleCreationFragment : BaseLoggedInFragment() {

    private val viewModel: RuleCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val playlistName = viewModel.playlistName.value
                val tags = viewModel.tags
                val optionality = viewModel.optionality.value
                val autoUpdate = viewModel.autoUpdate.value
                val allTags = viewModel.allTags

                val navController = findNavController()

                AppScaffold(
                    displayProgressBar = loading,
                    navController = navController,
                    screenTitle = stringResource(R.string.rule_creation_screen_title),
                    showBackButtonInTopBar = true
                ) {
                    Surface(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            val imageSize = 200.dp
                            Image( // TODO: To be implemented: add custom image
                                imageVector = Icons.Default.QueueMusic,
                                contentDescription = stringResource(R.string.new_rule_icon_description),
                                modifier = Modifier
                                    .height(imageSize)
                                    .width(imageSize),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            TextField(
                                value = playlistName,
                                onValueChange = {
                                    viewModel.onTriggerEvent(ChangePlaylistNameEvent(it)) // FIXME: Issue with backspace long press
                                },
                                label = { Text(stringResource(R.string.playlist_name)) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                )
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            FlowKeywordList(
                                keywordObjects = tags,
                                onAddNewKeyword = { tagName ->
                                    viewModel.onTriggerEvent(AddTagEvent(tagName))
                                },
                                newKeywordValidation = viewModel::canAddTag,
                                onClickDeleteIcon = { tag ->
                                    viewModel.onTriggerEvent(DeleteTagEvent(tag))
                                },
                                textFieldLeadingIcon = {
                                    Icon(Icons.Filled.Tag, contentDescription = stringResource(R.string.tag_icon_description))
                                },
                                textFieldLabel = stringResource(R.string.add_tag_here),
                                predictions = allTags,
                                predictionFilter = { tag, currentValue ->
                                    !tags.contains(tag) && tag.name.contains(currentValue)
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    )
                            ) {
                                Checkbox(
                                    checked = !optionality,
                                    onCheckedChange = {
                                        viewModel.onTriggerEvent(SwitchOptionalityEvent)
                                    }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.add_songs_with_all_tags),
                                    color = MaterialTheme.colors.onBackground,
                                    softWrap = true
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    )
                            ) {
                                Checkbox(
                                    checked = autoUpdate,
                                    onCheckedChange = {
                                        viewModel.onTriggerEvent(SwitchAutoUpdateEvent)
                                    }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.autoupdate),
                                    color = MaterialTheme.colors.onBackground,
                                    softWrap = true
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.onTriggerEvent(CreateRuleEvent {
                                            navController.popBackStack()
                                        })
                                    },
                                    enabled = tags.isNotEmpty() && playlistName.isNotBlank()
                                ) {
                                    Text(stringResource(R.string.create_rule))
                                }
                                Button(onClick = { navController.popBackStack() }) {
                                    Text(stringResource(R.string.cancel))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}