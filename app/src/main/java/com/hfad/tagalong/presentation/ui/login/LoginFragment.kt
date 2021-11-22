package com.hfad.tagalong.presentation.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_URI
import com.hfad.tagalong.presentation.components.LoginButton
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.login.LoginEvent.ReceiveLoginCodeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val isLoggedIn = viewModel.isLoggedIn.value

                AppTheme {
                    if (isLoggedIn) {
                        findNavController().navigate(R.id.login)
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LoginButton(
                                iconDrawable = R.drawable.ic_spotify_icon_green,
                                text = "LOG IN WITH SPOTIFY",
                                onClick = ::launchAuthUrl
                            )
                            Spacer(modifier = Modifier.height(36.dp))
                            Row {
                                Checkbox(
                                    checked = true,
                                    onCheckedChange = {}
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Stay logged in",
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun launchAuthUrl() {
        val authUri = viewModel.buildAuthUri()
        CustomTabsIntent.Builder().build().launchUrl(requireActivity(), authUri)
    }

    override fun onResume() {
        super.onResume()
        this.arguments?.get(BUNDLE_KEY_URI)?.let { uri ->
            viewModel.onTriggerEvent(ReceiveLoginCodeEvent(uri = uri as Uri))
        }
    }
}