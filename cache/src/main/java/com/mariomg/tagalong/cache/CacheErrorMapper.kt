package com.mariomg.tagalong.cache

import android.database.sqlite.SQLiteConstraintException
import com.mariomg.tagalong.interactors_core.data.ErrorType
import com.mariomg.tagalong.interactors_core.data.ErrorType.CacheError.DuplicateError
import com.mariomg.tagalong.interactors_core.data.ErrorType.CacheError.UnknownCacheError
import com.mariomg.tagalong.interactors_core.data.ErrorMapper

class CacheErrorMapper : ErrorMapper {

    override fun parseError(throwable: Throwable): ErrorType {
        return when (throwable) {
            is SQLiteConstraintException -> DuplicateError()
            else -> UnknownCacheError
        }
    }

}