package com.hfad.tagalong.presentation.ui.login

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.di.APP_CLIENT_ID
import com.hfad.tagalong.di.APP_REDIRECT_URI
import com.hfad.tagalong.presentation.ui.login.LoginEvent.*
import com.hfad.tagalong.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    @Named(APP_CLIENT_ID) private val clientId: String,
    @Named(APP_REDIRECT_URI) private val redirectUri: String,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val stayLoggedIn = mutableStateOf(true)

    private lateinit var codeVerifier: String

    private lateinit var codeChallenge: String

    init {
        viewModelScope.launch {
            stayLoggedIn.value = settingsRepository.loadStayLoggedIn()
        }
    }

    // Source: https://auth0.com/docs/flows/call-your-api-using-the-authorization-code-flow-with-pkce
    private fun generateCodeVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return Base64.encodeToString(code, ENCODING_FLAGS)
    }

    // Source: https://auth0.com/docs/flows/call-your-api-using-the-authorization-code-flow-with-pkce
    private fun generateCodeChallenge(): String {
        val bytes: ByteArray = codeVerifier.toByteArray(Charset.forName("US-ASCII"))
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        return Base64.encodeToString(digest, ENCODING_FLAGS)
    }

    fun onTriggerEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is ChangeStayLoggedInOptionEvent -> {
                    changeStayLoggedInOption()
                }
                is ClickLoginButtonEvent -> {
                    onClickLoginButton(event.context)
                }
                is ReceiveLoginCodeEvent -> {
                    login(event.uri)
                }
            }
        }
    }

    private suspend fun changeStayLoggedInOption() {
        stayLoggedIn.value = !stayLoggedIn.value
        settingsRepository.saveStayLoggedIn(stayLoggedIn.value)
    }

    private fun onClickLoginButton(context: Context) {
        codeVerifier = generateCodeVerifier()
        codeChallenge = generateCodeChallenge()
        val authUri = buildAuthUri()
        CustomTabsIntent.Builder().build().launchUrl(context, authUri)
    }

    private fun buildAuthUri(): Uri {
        return Uri.Builder()
            .scheme("https")
            .authority("accounts.spotify.com")
            .appendPath("en")
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("code_challenge_method", "S256")
            .appendQueryParameter("code_challenge", codeChallenge)
            .appendQueryParameter("scope", SCOPES.joinToString(","))
            .build()
    }

    private suspend fun login(uri: Uri) {
        val code = uri.getQueryParameter("code")
        code?.let {
            sessionManager.login(
                code = code,
                codeVerifier = codeVerifier,
                redirectUri = redirectUri,
                stayLoggedIn = stayLoggedIn.value
            )
        }
    }

    companion object {

        private val SCOPES = arrayOf(
            "playlist-read-private",
            "playlist-read-collaborative",
            "playlist-modify-public",
            "playlist-modify-private",
            "user-library-read"
        )

        private const val ENCODING_FLAGS = Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING

    }

}