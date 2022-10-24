package com.example.forest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forest.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userUserEntityDao(): UserEntityDao
}