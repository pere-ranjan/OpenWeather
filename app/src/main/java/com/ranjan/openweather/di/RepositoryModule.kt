package com.ranjan.openweather.di

import com.ranjan.openweather.common.NetworkUtils
import com.ranjan.openweather.data.UserRepositoryImpl
import com.ranjan.openweather.data.WeatherRepositoryImpl
import com.ranjan.openweather.data.database.UsersDao
import com.ranjan.openweather.data.database.WeatherDao
import com.ranjan.openweather.data.remote.ApiService
import com.ranjan.openweather.domain.repository.UserRepository
import com.ranjan.openweather.domain.repository.WeatherRepository
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


    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: ApiService, weatherDao: WeatherDao, networkUtils: NetworkUtils
    ): WeatherRepository = WeatherRepositoryImpl(apiService, weatherDao, networkUtils)

}