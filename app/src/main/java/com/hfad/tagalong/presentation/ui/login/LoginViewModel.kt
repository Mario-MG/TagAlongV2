package com.hfad.tagalong.presentation.ui.login

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.di.APP_CLIENT_ID
import com.hfad.tagalong.di.APP_REDIRECT_URI
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.interactors.data.ErrorType.NetworkError.AccessDeniedError
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.login.GetTokenFromCode
import com.hfad.tagalong.interactors.login.LoadUser
import com.hfad.tagalong.interactors.login.SaveSessionInfo
import com.hfad.tagalong.interactors.settings.LoadStayLoggedIn
import com.hfad.tagalong.interactors.settings.SaveStayLoggedIn
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.login.LoginEvent.*
import com.hfad.tagalong.presentation.util.DialogQueue
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
    private val getTokenFromCode: GetTokenFromCode,
    private val loadUser: LoadUser,
    private val saveSessionInfo: SaveSessionInfo,
    private val loadStayLoggedIn: LoadStayLoggedIn,
    private val saveStayLoggedIn: SaveStayLoggedIn,
    private val sessionManager: SessionManager,
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
            .appendPath(BaseApplication.getContext().getString(R.string.spotify_auth_uri_lang_path))
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
            getTokenFromCode
                .execute(
                    clientId = clientId,
                    code = code,
                    codeVerifier = codeVerifier,
                    redirectUri = redirectUri
                )
                .on(
                    success = { token ->
                        loadUser(token = token)
                        if (stayLoggedIn) {
                            saveSessionInfo(token)
                        }
                    },
                    error = ::appendGenericErrorToQueue
                )
                .launchIn(viewModelScope)
        } ?: run {
            dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.generic_spotify_error))
        }
    }

    private fun loadUser(token: Token) {
        loadUser
            .execute(token = token)
            .on(
                success = { user ->
                    sessionManager.login(
                        token = token,
                        user = user
                    )
                },
                error = { error ->
                    when (error) {
                        is AccessDeniedError -> dialogQueue.appendErrorDialog(
                            title = BaseApplication.getContext().getString(R.string.access_denied_error_title),
                            description = BaseApplication.getContext().getString(R.string.access_denied_error_description)
                        )
                        else -> appendGenericErrorToQueue(error)
                    }
                }
            )
            .launchIn(viewModelScope)
    }

    private fun saveSessionInfo(token: Token) {
        saveSessionInfo
            .execute(token = token)
            .on(
                error = { dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.session_unsaved_error_description)) }
            )
            .launchIn(viewModelScope)
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