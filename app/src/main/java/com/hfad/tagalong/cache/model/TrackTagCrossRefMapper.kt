package com.hfad.tagalong.cache.model

import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track

class TrackTagCrossRefMapper(
    private val trackEntityMapper: TrackEntityMapper,
    private val tagEntityMapper: TagEntityMapper
) {

    fun mapFromDomainModel(track: Track, tag: Tag): TrackTagCrossRef =
        TrackTagCrossRef(
            trackId = trackEntityMapper.mapFromDomainModel(track).id,
            tagId = tagEntityMapper.mapFromDomainModel(tag).tagEntity.id
        )

}