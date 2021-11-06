package com.hfad.tagalong.presentation.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_URI
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.login.LoginEvent.ReceiveLoginCodeEvent
import dagger.hilt.android.AndroidEntryPoint

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
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(
                            onClick = {
                                launchAuthUrl()
                            },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(text = "Login")
                        }
                    }
                }
            }
        }
    }

    private fun launchAuthUrl() {
        val authUri = viewModel.buildAuthUri()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.flags =
            Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP // TODO: Is this necessary?
        customTabsIntent.launchUrl(requireActivity(), authUri)
    }

    override fun onResume() {
        super.onResume()
        this.arguments?.get(BUNDLE_KEY_URI)?.let { uri ->
            viewModel.onTriggerEvent(ReceiveLoginCodeEvent(uri = uri as Uri))
        }
    }
}