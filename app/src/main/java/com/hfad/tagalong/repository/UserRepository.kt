package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.User

interface UserRepository {

    suspend fun get(auth: String): User

}