package com.ranjan.openweather.di

import com.ranjan.openweather.data.UserRepositoryImpl
import com.ranjan.openweather.data.database.UsersDao
import com.ranjan.openweather.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(usersDao: UsersDao): UserRepository = UserRepositoryImpl(usersDao)

}