package com.hfad.tagalong.cache.model

import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track

class TrackTagCrossRefMapper {

    fun mapFromDomainModel(track: Track, tag: Tag): TrackTagCrossRef =
        TrackTagCrossRef(trackId = track.uri, tagId = tag.id)

}