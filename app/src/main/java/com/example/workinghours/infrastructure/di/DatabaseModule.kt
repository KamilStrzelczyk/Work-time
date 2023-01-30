package com.example.workinghours.infrastructure.di

import android.content.Context
import androidx.room.Room
import com.example.workinghours.infrastructure.database.AppDatabase
import com.example.workinghours.infrastructure.database.dao.AdminDao
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.getUserDao()

    @Provides
    fun provideWorkDataDao(appDatabase: AppDatabase): WorkDataDao =
        appDatabase.getWorkDataDao()

    @Provides
    fun provideAdminDao(appDatabase: AppDatabase): AdminDao =
        appDatabase.getAdminDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "Users",
        ).build()
}