package com.example.appsale12102022.data.remote
open class AppResource<T> (var data: T?, var message: String?, var status: Status) {
    constructor(data: T?, status: Status): this(data, null, status)
    constructor(message: String, status: Status): this(null, message, status)
    class Success<T>(data: T?): AppResource<T>(data, Status.SUCCESS)
    class Error<T>(message: String): AppResource<T>(message, Status.ERROR)
    class Loading<T>(data: T?): AppResource<T>(data, Status.LOADING)
    enum class Status {
        SUCCESS, LOADING, ERROR
    }
}
