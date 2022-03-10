package com.example.rssfeed.utils

sealed class Status<T> {
    class Success<T>(val data: T) : Status<T>()
    class Error<T>(val message: String) : Status<T>()
    class Loading<T>: Status<T>()
}
