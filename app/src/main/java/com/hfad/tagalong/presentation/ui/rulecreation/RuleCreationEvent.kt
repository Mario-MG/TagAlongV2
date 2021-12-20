package com.hfad.tagalong.presentation.ui.rulecreation

import com.hfad.tagalong.domain.model.Tag

sealed class RuleCreationEvent {

    object InitRuleCreationEvent : RuleCreationEvent()

    data class ChangePlaylistNameEvent(val playlistName: String): RuleCreationEvent()

    data class AddTagEvent(val tagName: String) : RuleCreationEvent()

    data class DeleteTagEvent(val tag: Tag) : RuleCreationEvent()

    object SwitchOptionalityEvent : RuleCreationEvent()

    object SwitchAutoUpdateEvent : RuleCreationEvent()

    data class CreateRuleEvent(val callback: () -> Unit = {}) : RuleCreationEvent()

}
