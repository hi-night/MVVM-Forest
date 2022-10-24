package com.example.forest.entity

class BaseResult<T> {
    val data: T? = null
    val errorCode: Int = 0
    var errorMsg: String = ""

    fun isSuccessful(): Boolean {
        return errorCode == 0
    }
}