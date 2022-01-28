package com.hfad.tagalong.tag_interactors_core.repositories

import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track

interface TagCacheRepository {

    suspend fun getAll(): List<Tag>

    suspend fun getTagByName(tagName: String): Tag?

    suspend fun getTagsForTrack(track: Track): List<Tag>

    suspend fun createNewTag(tagName: String): Tag

}