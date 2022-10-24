package com.example.forest.base

import com.example.forest.mvvm.base.viewstate.IViewState


sealed class Results<out T> : IViewState {

    companion object {
        fun <T> success(result: T?): Results<T?> = Success(result)
        fun <T> failure(code: Int, message: String): Results<T> = Failure(code, message)
    }

    data class Success<out T>(val data: T) : Results<T>()
    data class Failure(val code: Int, val message: String) : Results<Nothing>()
}


inline fun <reified T> Results<T>.isSuccess(success: (T) -> Unit) {
    if (this is Results.Success) {
        success(data)
    }
}

inline fun <reified T> Results<T>.isFailure(failure: (Results.Failure) -> Unit) {
    if (this is Results.Failure) {
        failure(Results.Failure(code, message))
    }
}
