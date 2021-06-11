package com.attafakkur.core.data

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : State<T>(data)
    class Loading<T>(data: T? = null) : State<T>(data)
    class Error<T>(message: String, data: T? = null) : State<T>(data, message)
    class Empty<T>(message: String, data: T? = null) : State<T>(data, message)
}