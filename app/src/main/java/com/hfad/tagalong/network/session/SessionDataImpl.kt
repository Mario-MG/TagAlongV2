package com.hfad.tagalong.network.session

import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.network.session.model.Token
import com.hfad.tagalong.network.session.model.User
import kotlinx.serialization.Serializable

@Serializable
class SessionDataImpl(
    val token: Token,
    val user: User
) : SessionData()