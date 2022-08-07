package com.mariomg.tagalong.network_core.util

import com.mariomg.tagalong.interactors_core.data.ErrorType
import com.mariomg.tagalong.interactors_core.data.ErrorType.NetworkError.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class NetworkErrorMapper : ErrorMapper {

    override fun parseError(throwable: Throwable): ErrorType {
        return when(throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    HTTP_UNAUTHORIZED -> UnauthorisedError()
                    HTTP_FORBIDDEN -> AccessDeniedError()
                    else -> ServiceUnavailableError()
                }
            }
            else -> UnknownNetworkError
        }
    }

}