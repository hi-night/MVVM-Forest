package com.example.forest.di

import androidx.room.Room
import com.example.forest.base.ForestApplication
import com.example.forest.db.UserDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    fun provideUserDatabase(application: ForestApplication): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "user")
            .fallbackToDestructiveMigration()
            .build()
    }
}