package com.mariomg.tagalong.cache.model

import com.mariomg.tagalong.domain_common.DomainMapper
import com.mariomg.tagalong.playlist_domain.Playlist

class PlaylistEntityMapper : DomainMapper<Playlist, PlaylistEntity> {

    override fun mapToDomainModel(model: PlaylistEntity): Playlist {
        return Playlist(
            id = model.id,
            name = model.name,
            size = 0, // TODO
            imageUrl = null // TODO
        )
    }

    override fun mapFromDomainModel(domainModel: Playlist): PlaylistEntity {
        return PlaylistEntity(
            id = domainModel.id,
            name = domainModel.name
        )
    }

}