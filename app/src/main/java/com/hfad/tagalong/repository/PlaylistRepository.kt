package com.hfad.tagalong.repository

import com.hfad.tagalong.network.models.PlaylistDto
import com.hfad.tagalong.network.models.PlaylistListDto

interface PlaylistRepository {

    suspend fun getById(token: String, id: String): PlaylistDto // TODO: Change to domain model

    suspend fun getList(token: String, limit: Int = 20, offset: Int = 0): PlaylistListDto // TODO: Change to domain model

}