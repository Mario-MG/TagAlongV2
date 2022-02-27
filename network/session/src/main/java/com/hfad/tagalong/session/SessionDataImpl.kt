package com.hfad.tagalong.session

import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.session.model.Token
import com.hfad.tagalong.session.model.User
import kotlinx.serialization.Serializable

@Serializable
class SessionDataImpl(
    val token: Token,
    val user: User
) : SessionData()