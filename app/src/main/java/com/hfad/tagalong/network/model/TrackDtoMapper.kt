package com.hfad.tagalong.network.model

import com.hfad.tagalong.domain_common.DomainMapper
import com.hfad.tagalong.track_domain.Track

class TrackDtoMapper : DomainMapper<Track, TrackDto> {

    override fun mapToDomainModel(model: TrackDto): Track {
        return Track(
            id = model.uri.split(":").last(),
            name = model.name,
            album = model.album.name,
            artists = model.artists.map { it.name },
            imageUrl = if (model.album.images.isNotEmpty()) model.album.images[0].url else null,
            uri = model.uri
        )
    }

    override fun mapFromDomainModel(domainModel: Track): TrackDto {
        TODO("Not yet implemented")
    }

}