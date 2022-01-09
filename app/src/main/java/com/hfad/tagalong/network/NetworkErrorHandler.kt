package com.hfad.tagalong.network

import com.hfad.tagalong.interactors.data.ErrorType
import com.hfad.tagalong.interactors.data.ErrorType.NetworkError.*
import com.hfad.tagalong.interactors.data.ErrorHandler
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class NetworkErrorHandler : ErrorHandler {

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