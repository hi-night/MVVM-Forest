package com.example.forest.mvvm.http.utils

import com.example.forest.base.Results
import com.example.forest.entity.BaseResult
import retrofit2.Response

inline fun <T> processApiResponse(request: () -> Response<BaseResult<T>>): Results<T?> {
    val response = request()
    val body = response.body()
    return if (response.isSuccessful) {
        if (body?.errorCode == 0){
            Results.success(body.data)
        } else {
            Results.failure(body!!.errorCode, body.errorMsg)
        }
    } else {
        Results.failure(body!!.errorCode, body.errorMsg)
    }
}