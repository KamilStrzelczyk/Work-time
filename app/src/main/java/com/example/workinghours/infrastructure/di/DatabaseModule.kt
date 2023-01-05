package com.example.workinghours.infrastructure.di

import com.example.workinghours.infrastructure.database.AppDatabase
import com.example.workinghours.infrastructure.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDAO(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }
}