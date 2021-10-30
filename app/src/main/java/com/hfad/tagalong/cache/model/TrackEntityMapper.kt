package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.domain.util.DomainMapper

class TrackEntityMapper : DomainMapper<TrackEntity, Track> {

    override fun mapToDomainModel(model: TrackEntity): Track {
        return Track(
            id = model.id,
            name = model.name,
            album = model.album,
            artists = model.artists.split(", "), // TODO: Come up with a better way to store artists
            imageUrl = model.imageUrl
        )
    }

    override fun mapFromDomainModel(domainModel: Track): TrackEntity {
        return TrackEntity(
            id = domainModel.id,
            name = domainModel.name,
            album = domainModel.album,
            artists = domainModel.artists.joinToString(", "), // TODO: Come up with a better way to store artists
            imageUrl = domainModel.imageUrl
        )
    }

}