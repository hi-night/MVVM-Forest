package com.example.forest.ui.login

import com.example.forest.base.Results
import com.example.forest.entity.BaseResult
import com.example.forest.entity.UserEntity
import com.example.forest.mvvm.base.repository.BaseRepositoryRemote
import com.example.forest.mvvm.base.repository.IRemoteDataSource
import com.example.forest.mvvm.http.service.UserService
import com.example.forest.mvvm.http.utils.processApiResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(dataSource: UserRemoteDataSource) :
    BaseRepositoryRemote<UserRemoteDataSource>(dataSource) {

    suspend fun login(userName: String, password: String): Results<UserEntity?> {
        return remoteDataSource.login(userName, password)
    }
}


class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : IRemoteDataSource {

    suspend fun login(userName: String, password: String): Results<UserEntity?> {
        return processApiResponse {
            userService.login(userName, password)
        }
    }
}