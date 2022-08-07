package com.mariomg.tagalong.session

import com.mariomg.tagalong.auth_interactors_core.session.SessionData
import com.mariomg.tagalong.session.model.Token
import com.mariomg.tagalong.session.model.User
import kotlinx.serialization.Serializable

@Serializable
class SessionDataImpl(
    val token: Token,
    val user: User
) : SessionData()