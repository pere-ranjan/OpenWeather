package com.ranjan.openweather.di

import android.content.Context
import com.ranjan.openweather.data.database.ApplicationDatabase
import com.ranjan.openweather.data.database.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDao(database: ApplicationDatabase) = database.getUsersDao()
    @Provides
    @Singleton
    fun provideWeatherDao(applicationDatabase: ApplicationDatabase): WeatherDao = applicationDatabase.getWeatherDao()

    @Provides
    @Singleton
    fun provideUserDatabase(
        @ApplicationContext context: Context,
    ): ApplicationDatabase {
        return ApplicationDatabase.getInstance(context)
    }
}