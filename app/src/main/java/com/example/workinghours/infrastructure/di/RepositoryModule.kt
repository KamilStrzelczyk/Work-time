package com.example.workinghours.infrastructure.di

import com.example.workinghours.domain.repository.AdminRepository
import com.example.workinghours.domain.repository.UserRepository
import com.example.workinghours.domain.repository.WorkDataRepository
import com.example.workinghours.infrastructure.repository.AdminRepositoryImpl
import com.example.workinghours.infrastructure.repository.UserRepositoryImpl
import com.example.workinghours.infrastructure.repository.WorkDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsWorkDataRepository(impl: WorkDataRepositoryImpl): WorkDataRepository

    @Binds
    fun bindsAdminRepository(impl: AdminRepositoryImpl): AdminRepository

}