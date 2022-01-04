package com.hfad.tagalong.interactors.data

sealed class ErrorType(open val message: String) {

    sealed class NetworkError(override val message: String = "Network Error") : ErrorType(message) {

        data class UnauthorisedError(override val message: String = "Unauthorised Access Error") : NetworkError(message)

        data class AccessDeniedError(override val message: String = "Access Denied Error") : NetworkError(message)

        data class ServiceUnavailableError(override val message: String = "Service Unavailable Error") : NetworkError(message)

        object UnknownNetworkError : NetworkError("Unknown Network Error")

    }

    sealed class CacheError(override val message: String = "Cache Error") : ErrorType(message) {

        data class DuplicateError(override val message: String = "Duplicate Insertion Error") : CacheError(message)

        object UnknownCacheError : CacheError("Unknown Error")

    }

}
