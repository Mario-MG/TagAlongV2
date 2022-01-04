package com.hfad.tagalong.presentation.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.components.EmptyListPlaceholderText
import com.hfad.tagalong.presentation.components.RuleItemList
import com.hfad.tagalong.presentation.theme.AppScaffold
import com.hfad.tagalong.presentation.ui.BaseLoggedInFragment
import com.hfad.tagalong.presentation.ui.Screen
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class RulesFragment : BaseLoggedInFragment() {

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

                val dialogQueue = viewModel.dialogQueue

                val navController = findNavController()

                AppScaffold(navController = navController)
                    .withProgressBar(
                        displayProgressBar = loading,
                        progressBarAlignment = if (rules.isEmpty()) Alignment.TopCenter else Alignment.BottomCenter
                    )
                    .withNavBar()
                    .withTopBar(
                        screenTitle = Screen.Rules.getLabel(),
                        helpContent = { Text(stringResource(R.string.rules_help)) }
                    )
                    .withFloatingActionButton {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(R.id.createRule)
                            },
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_rule_icon_description))
                        }
                    }
                    .withDialog(dialogQueue.currentDialog.value)
                    .setContent {
                        if (rules.isNotEmpty()) {
                            RuleItemList(
                                rules = rules,
                                onClickRuleItem = {}
                            )
                        } else if (!loading) {
                            EmptyListPlaceholderText(text = stringResource(R.string.no_rules))
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