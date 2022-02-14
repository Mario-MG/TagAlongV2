package com.hfad.tagalong.network.model

import com.hfad.tagalong.domain_common.DomainMapper
import com.hfad.tagalong.playlist_domain.Playlist

class PlaylistDtoMapper : DomainMapper<Playlist, PlaylistDto> {

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