package com.example.workinghours.infrastructure.di

import android.content.Context
import androidx.room.Room
import com.example.workinghours.infrastructure.database.AppDatabase
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
    fun provideUserDAO(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }
    @Provides
    fun provideWorkData(appDatabase: AppDatabase): WorkDataDao{
        return appDatabase.getWorkDataDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context):
            AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Users"
        ).build()
    }
}