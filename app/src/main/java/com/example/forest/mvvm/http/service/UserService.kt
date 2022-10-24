package com.example.forest.mvvm.http.service

import com.example.forest.entity.BaseResult
import com.example.forest.entity.HomeEntity
import com.example.forest.entity.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("user/login")
    suspend fun login(
        @Query("username") userName: String,
        @Query("password") password: String,
    ): Response<BaseResult<UserEntity>>


    @GET("https://www.wanandroid.com/article/list/{pageSize}/json")
    suspend fun getArticleList(
        @Path("pageSize")  pageSize: String
    ): BaseResult<HomeEntity>

}