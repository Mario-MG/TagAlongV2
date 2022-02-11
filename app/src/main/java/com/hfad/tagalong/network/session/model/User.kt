package com.hfad.tagalong.network.session.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String?,
    val id: String
)