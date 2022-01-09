package com.hfad.tagalong.interactors.data

interface ErrorHandler {

    fun parseError(throwable: Throwable): ErrorType

}