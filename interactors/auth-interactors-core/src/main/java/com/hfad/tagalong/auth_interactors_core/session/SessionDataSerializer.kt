package com.hfad.tagalong.auth_interactors_core.session

interface SessionDataSerializer {

    fun serializeSessionData(sessionData: SessionData): String

    fun deserializeSessionData(string: String): SessionData

}