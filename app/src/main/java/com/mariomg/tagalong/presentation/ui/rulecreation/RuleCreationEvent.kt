package com.mariomg.tagalong.presentation.ui.rulecreation

import com.mariomg.tagalong.tag_domain.Tag

sealed class RuleCreationEvent {

    object InitRuleCreationEvent : RuleCreationEvent()

    data class ChangePlaylistNameEvent(val playlistName: String) : RuleCreationEvent()

    data class AddTagEvent(val tagName: String) : RuleCreationEvent()

    data class DeleteTagEvent(val tag: Tag) : RuleCreationEvent()

    object SwitchOptionalityEvent : RuleCreationEvent()

    object SwitchAutoUpdateEvent : RuleCreationEvent()

    object CreateRuleEvent : RuleCreationEvent()

}
