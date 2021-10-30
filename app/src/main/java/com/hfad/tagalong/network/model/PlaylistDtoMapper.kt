package com.hfad.tagalong.network.model

import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.util.DomainMapper

class PlaylistDtoMapper: DomainMapper<PlaylistDto, Playlist> {

    override fun mapToDomainModel(model: PlaylistDto): Playlist {
        return Playlist(
            id = model.id,
            name = model.name,
            size = model.tracks.total,
            imageUrl = if (model.images.isNotEmpty()) model.images[0].url else null
        )
    }

    override fun mapFromDomainModel(domainModel: Playlist): PlaylistDto {
        TODO("Not yet implemented")
    }

}