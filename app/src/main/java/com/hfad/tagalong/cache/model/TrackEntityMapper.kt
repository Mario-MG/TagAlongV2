package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain_common.DomainMapper
import com.hfad.tagalong.track_domain.Track

class TrackEntityMapper : DomainMapper<Track, TrackEntity> {

    override fun mapToDomainModel(model: TrackEntity): Track {
        return Track(
            id = model.id.split(":").last(),
            name = model.name,
            album = model.album,
            artists = model.artists.split(", "), // TODO: Come up with a better way to store artists
            imageUrl = model.imageUrl,
            uri = model.id
        )
    }

    override fun mapFromDomainModel(domainModel: Track): TrackEntity {
        return TrackEntity(
            id = domainModel.uri,
            name = domainModel.name,
            album = domainModel.album,
            artists = domainModel.artists.joinToString(", "), // TODO: Come up with a better way to store artists
            imageUrl = domainModel.imageUrl
        )
    }

}