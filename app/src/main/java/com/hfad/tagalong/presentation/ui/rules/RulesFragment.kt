package com.hfad.tagalong.presentation.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.AppNavigationBar
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.components.RuleItemList
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.CreateNewRuleEvent
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class RulesFragment : Fragment() {

    private val viewModel: RulesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val rules = viewModel.rules
                val loading = viewModel.loading.value
                val navController = findNavController()

                AppTheme(
                    displayProgressBar = loading,
                    progressBarAlignment = if (rules.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                ) {
                    Scaffold(
                        bottomBar = {
                            AppNavigationBar(
                                navController = navController
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(R.id.createRule)
                                }
                            ) {
                                Icon(Icons.Filled.Add,"Add Rule icon")
                            }
                        }
                    ) { innerPadding -> // TODO: Include this in all Scaffold uses
                        Box(modifier = Modifier.padding(innerPadding)) {
                            if (rules.isNotEmpty()) {
                                RuleItemList(
                                    rules = rules,
                                    onClickRuleItem = {}
                                )
                            } else if (!loading) {
                                EmptyListPlaceholderText(text = "There are no rules to show")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(LoadRulesEvent)
    }

}