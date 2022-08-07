package com.mariomg.tagalong.presentation.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariomg.tagalong.R
import com.mariomg.tagalong.presentation.BUNDLE_KEY_URI
import com.mariomg.tagalong.presentation.LOGIN_SUCCESSFUL
import com.mariomg.tagalong.presentation.components.LoginButton
import com.mariomg.tagalong.presentation.theme.AppScaffold
import com.mariomg.tagalong.presentation.ui.login.LoginEvent.*
import com.mariomg.tagalong.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val stayLoggedIn = viewModel.stayLoggedIn

                val mainDialogQueue = mainViewModel.dialogQueue
                val dialogQueue = viewModel.dialogQueue

                AppScaffold(navController = findNavController())
                    .withDialog(mainDialogQueue.currentDialog ?: dialogQueue.currentDialog)
                    .setContent {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LoginButton(
                                iconDrawable = R.drawable.ic_spotify_icon_green,
                                text = stringResource(R.string.log_in_with_spotify),
                                onClick = {
                                    viewModel.onTriggerEvent(ClickLoginButtonEvent(requireActivity()))
                                }
                            )
                            Spacer(modifier = Modifier.height(36.dp))
                            Row {
                                Checkbox(
                                    checked = stayLoggedIn,
                                    onCheckedChange = {
                                        viewModel.onTriggerEvent(ChangeStayLoggedInOptionEvent)
                                    }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.stay_logged_in),
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Source: https://youtu.be/09qjn706ITA?t=284
        val navController = findNavController()
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
            ?: throw IllegalStateException("LoginFragment must not be a start destination")
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)
        mainViewModel.addLoginSuccessObserver(viewLifecycleOwner) {
            savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            navController.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        this.arguments?.get(BUNDLE_KEY_URI)?.let { uri ->
            viewModel.onTriggerEvent(ReceiveLoginCodeEvent(uri = uri as Uri))
        }
    }
}