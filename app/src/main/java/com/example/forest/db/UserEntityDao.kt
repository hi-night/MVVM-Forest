package com.example.forest.db

import androidx.room.Dao
import androidx.room.Query
import com.example.forest.entity.UserEntity

@Dao
interface UserEntityDao {
    @Query("select * from user ")
    fun getUserEntity(): UserEntity
}