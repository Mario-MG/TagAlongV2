package com.hfad.tagalong.presentation.util

import androidx.compose.runtime.mutableStateOf
import com.hfad.tagalong.presentation.util.DialogData.ErrorDialog
import com.hfad.tagalong.presentation.util.DialogData.InfoDialog
import java.util.*

class DialogQueue {

    val currentDialog = mutableStateOf<DialogData?>(null)

    private val queue = LinkedList<DialogData>()

    fun appendInfoDialog(description: String, title: String = "Info") {
        append(
            InfoDialog(
                description = description,
                title = title,
                onDismiss = this::dismissCurrentDialog
            )
        )
    }

    fun appendErrorDialog(description: String, title: String = "Error") {
        append(
            ErrorDialog(
                description = description,
                title = title,
                onDismiss = this::dismissCurrentDialog
            )
        )
    }

    private fun append(dialogData: DialogData) {
        queue.offer(dialogData)
        if (currentDialog.value == null) {
            updateCurrentValue()
        }
    }

    fun dismissCurrentDialog() {
        queue.poll()
        updateCurrentValue()
    }

    private fun updateCurrentValue() {
        currentDialog.value = queue.peek()
    }

}

sealed class DialogData(
    open val description: String,
    open val title: String,
    open val onDismiss: () -> Unit
) {

    class InfoDialog internal constructor(
        override val description: String,
        override val title: String,
        override val onDismiss: () -> Unit
    ) : DialogData(
        description = description,
        title = title,
        onDismiss = onDismiss
    )

    class ErrorDialog internal constructor(
        override val description: String,
        override val title: String,
        override val onDismiss: () -> Unit
    ) : DialogData(
        description = description,
        title = title,
        onDismiss = onDismiss
    )

}