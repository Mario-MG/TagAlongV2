package com.mariomg.tagalong.tag_interactors_core.repositories

import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.tag_domain.TagInfo
import com.mariomg.tagalong.track_domain.Track

interface TagCacheRepository {

    suspend fun getAll(): List<Tag>

    suspend fun getTagByName(tagName: String): Tag?

    suspend fun getTagsForTrack(track: Track): List<Tag>

    suspend fun createNewTag(tagInfo: TagInfo): Tag

}