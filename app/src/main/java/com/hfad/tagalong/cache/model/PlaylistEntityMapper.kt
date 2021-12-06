package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.util.DomainMapper

class PlaylistEntityMapper : DomainMapper<PlaylistEntity, Playlist> {

    override fun mapToDomainModel(model: PlaylistEntity): Playlist {
        return Playlist(
            id = model.id,
            name = model.name,
            size = 0 // TODO
        )
    }

    override fun mapFromDomainModel(domainModel: Playlist): PlaylistEntity {
        return PlaylistEntity(
            id = domainModel.id,
            name = domainModel.name
        )
    }

}