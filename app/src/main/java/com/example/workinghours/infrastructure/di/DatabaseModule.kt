package com.example.workinghours.infrastructure.di

import android.content.Context
import androidx.room.Room
import com.example.workinghours.infrastructure.database.AppDatabase
import com.example.workinghours.infrastructure.database.dao.AdminDao
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }

    @Provides
    fun provideWorkDataDao(appDatabase: AppDatabase): WorkDataDao {
        return appDatabase.getWorkDataDao()
    }

    @Provides
    fun provideAdminDao(appDatabase: AppDatabase): AdminDao {
        return appDatabase.getAdminDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context):
            AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Users",
        ).build()
    }
}