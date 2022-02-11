package com.hfad.tagalong.network.session

import com.hfad.tagalong.session.SessionData
import com.hfad.tagalong.session.SessionDataSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionDataSerializerImpl : SessionDataSerializer {

    override fun serializeSessionData(sessionData: SessionData): String {
        if (sessionData !is SessionDataImpl) throw IllegalArgumentException()
        return Json.encodeToString(sessionData)
    }

    override fun deserializeSessionData(string: String): SessionData {
        return Json.decodeFromString<SessionDataImpl>(string)
    }

}