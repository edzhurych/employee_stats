package com.example.domain.response

sealed class ResponseWrapper<out T> {
    data class Loading(val isLoading: Boolean) : ResponseWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResponseWrapper<T>()
    data class Failure(val code: Int?, val message: String?): ResponseWrapper<Nothing>()
}
