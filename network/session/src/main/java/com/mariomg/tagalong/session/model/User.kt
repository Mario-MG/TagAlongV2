package com.mariomg.tagalong.session.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String?,
    val id: String
)