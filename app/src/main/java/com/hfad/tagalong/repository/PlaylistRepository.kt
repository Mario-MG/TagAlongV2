package com.hfad.tagalong.repository

import com.hfad.tagalong.network.models.PlaylistDto

interface PlaylistRepository {

    suspend fun get(token: String, id: String): PlaylistDto // TODO: Change to domain model

}