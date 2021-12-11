package com.hfad.tagalong.presentation.session

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.model.User

data class Session(
    val token: Token,
    val user: User
)