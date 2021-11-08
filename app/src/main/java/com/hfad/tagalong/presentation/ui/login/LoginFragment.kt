package com.hfad.tagalong.presentation.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.BUNDLE_KEY_URI
import com.hfad.tagalong.presentation.theme.AppTheme
import com.hfad.tagalong.presentation.ui.login.LoginEvent.ReceiveLoginCodeEvent
import com.hfad.tagalong.util.loadPicture
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
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Button(
                                onClick = ::launchAuthUrl,
                                modifier = Modifier
                                    .align(Alignment.Center),
                                shape = RoundedCornerShape(48.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 16.dp,
                                            horizontal = 8.dp
                                        )
                                ) {
                                    val spotifyIcon = loadPicture(
                                        drawable = R.drawable.ic_spotify_icon_green
                                    ).value
                                    spotifyIcon?.let {
                                        Icon(
                                            bitmap = spotifyIcon.asImageBitmap(),
                                            contentDescription = "Spotify Icon",
                                            modifier = Modifier
                                                .size(36.dp)
                                                .align(Alignment.CenterVertically),
                                            tint = MaterialTheme.colors.onPrimary
                                        )
                                        Spacer(modifier = Modifier.size(20.dp))
                                    }
                                    Text(
                                        text = "LOG IN WITH SPOTIFY",
                                        style = MaterialTheme.typography.h3,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                    )
                                }
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