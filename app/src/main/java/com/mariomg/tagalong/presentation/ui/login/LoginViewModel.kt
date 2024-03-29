package com.mariomg.tagalong.presentation.ui.login

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mariomg.tagalong.R
import com.mariomg.tagalong.auth_interactors.GetNewSessionData
import com.mariomg.tagalong.auth_interactors_core.session.SessionManager
import com.mariomg.tagalong.di.APP_CLIENT_ID
import com.mariomg.tagalong.di.APP_REDIRECT_URI
import com.mariomg.tagalong.interactors_core.data.ErrorType.NetworkError.AccessDeniedError
import com.mariomg.tagalong.interactors_core.util.on
import com.mariomg.tagalong.presentation.BaseApplication
import com.mariomg.tagalong.presentation.ui.BaseViewModel
import com.mariomg.tagalong.presentation.ui.login.LoginEvent.*
import com.mariomg.tagalong.presentation.util.DialogQueue
import com.mariomg.tagalong.settings_interactors.LoadStayLoggedIn
import com.mariomg.tagalong.settings_interactors.SaveStayLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
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
    private val getNewSessionData: GetNewSessionData,
    private val sessionManager: SessionManager,
    private val loadStayLoggedIn: LoadStayLoggedIn,
    private val saveStayLoggedIn: SaveStayLoggedIn,
    @Named(APP_CLIENT_ID) private val clientId: String,
    @Named(APP_REDIRECT_URI) private val redirectUri: String
) : BaseViewModel() {

    var stayLoggedIn by mutableStateOf(true)
        private set

    override val dialogQueue = DialogQueue()

    private lateinit var codeVerifier: String

    private lateinit var codeChallenge: String

    init {
        loadStayLoggedIn()
    }

    private fun loadStayLoggedIn() {
        loadStayLoggedIn
            .execute()
            .on(
                success = ::stayLoggedIn::set,
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
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

    private fun changeStayLoggedInOption() {
        val newStayLoggedInValue = !stayLoggedIn
        saveStayLoggedIn
            .execute(stayLoggedIn = newStayLoggedInValue)
            .on(
                success = { stayLoggedIn = newStayLoggedInValue },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
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
            .appendPath(BaseApplication.getString(R.string.spotify_auth_uri_lang_path))
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("code_challenge_method", "S256")
            .appendQueryParameter("code_challenge", codeChallenge)
            .appendQueryParameter("scope", SCOPES.joinToString(","))
            .build()
    }

    private fun login(uri: Uri) {
        val code = uri.getQueryParameter("code")
        code?.let {
            getNewSessionData
                .execute(
                    code = code,
                    codeVerifier = codeVerifier
                )
                .on(
                    success = { sessionData ->
                        sessionManager.logIn(sessionData)
                    },
                    error = { error ->
                        when (error) {
                            is AccessDeniedError -> dialogQueue.appendErrorDialog(
                                title = BaseApplication.getContext()
                                    .getString(R.string.access_denied_error_title),
                                description = BaseApplication.getContext()
                                    .getString(R.string.access_denied_error_description)
                            )
                            else -> appendGenericErrorToQueue(error)
                        }
                    }
                )
                .launchIn(viewModelScope)
        } ?: run {
            dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.generic_spotify_error))
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