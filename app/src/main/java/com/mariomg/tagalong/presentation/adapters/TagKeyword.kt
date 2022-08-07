package com.mariomg.tagalong.presentation.adapters

import com.mariomg.tagalong.presentation.components.Keyword
import com.mariomg.tagalong.tag_domain.Tag

data class TagKeyword(
    val tag: Tag
) : Keyword {

    override fun keyword(): String = tag.name

}