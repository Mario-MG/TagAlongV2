package com.hfad.tagalong.presentation.ui.login

import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.di.APP_CLIENT_ID
import com.hfad.tagalong.di.APP_REDIRECT_URI
import com.hfad.tagalong.presentation.ui.login.LoginEvent.ReceiveLoginCodeEvent
import com.hfad.tagalong.repository.AuthRepository
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
    private val authRepository: AuthRepository,
    private val session: Session,
    @Named(APP_CLIENT_ID) private val clientId: String,
    @Named(APP_REDIRECT_URI) private val redirectUri: String
) : ViewModel() {

    private val codeVerifier: String

    private val codeChallenge: String

    private val scopes = arrayOf(
        "playlist-read-private",
        "playlist-read-collaborative",
        "playlist-modify-public",
        "playlist-modify-private",
        "user-library-read"
    )

    private val encodingFlags = Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING

    val isLoggedIn = mutableStateOf(session.isLoggedIn())

    init {
        codeVerifier = generateCodeVerifier()
        codeChallenge = generateCodeChallenge()
    }

    // Source: https://auth0.com/docs/flows/call-your-api-using-the-authorization-code-flow-with-pkce
    private fun generateCodeVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return Base64.encodeToString(code, encodingFlags)
    }

    // Source: https://auth0.com/docs/flows/call-your-api-using-the-authorization-code-flow-with-pkce
    private fun generateCodeChallenge(): String {
        val bytes: ByteArray = codeVerifier.toByteArray(Charset.forName("US-ASCII"))
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        return Base64.encodeToString(digest, encodingFlags)
    }

    fun onTriggerEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is ReceiveLoginCodeEvent -> {
                    login(event.uri)
                }
            }
        }
    }

    fun buildAuthUri(): Uri {
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
            .appendQueryParameter("scope", scopes.joinToString(","))
            .build()
    }

    private suspend fun login(uri: Uri) {
        val code = uri.getQueryParameter("code")
        code?.let {
            val token = authRepository.getNewToken(
                clientId = clientId,
                code = code,
                codeVerifier = codeVerifier,
                redirectUri = redirectUri
            )
            session.loginWithToken(token)
            isLoggedIn.value = session.isLoggedIn()
        }
    }

}