package com.ranjan.openweather.di

import android.content.Context
import com.ranjan.openweather.common.EncryptedSharedPreference
import com.ranjan.openweather.common.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Provides
    @Singleton
    fun providesEncryptedSharedPref(@ApplicationContext context: Context) = EncryptedSharedPreference(context)

    @Provides
    @Singleton
    fun providesNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)


}