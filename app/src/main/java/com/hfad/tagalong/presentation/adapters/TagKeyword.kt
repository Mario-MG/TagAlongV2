package com.hfad.tagalong.presentation.adapters

import com.hfad.tagalong.presentation.components.Keyword
import com.hfad.tagalong.tag_domain.Tag

data class TagKeyword(
    val tag: Tag
) : Keyword {

    override fun keyword(): String = tag.name

}