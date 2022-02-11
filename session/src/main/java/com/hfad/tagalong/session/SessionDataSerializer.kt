package com.hfad.tagalong.session

interface SessionDataSerializer {

    fun serializeSessionData(sessionData: SessionData): String

    fun deserializeSessionData(string: String): SessionData

}