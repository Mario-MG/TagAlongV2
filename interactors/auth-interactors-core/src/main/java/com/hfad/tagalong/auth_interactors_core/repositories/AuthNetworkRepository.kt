package com.hfad.tagalong.auth_interactors_core.repositories

interface AuthNetworkRepository {

    suspend fun logIn(codeVerifier: String, code: String)

    suspend fun refresh()

    suspend fun logOut()

}