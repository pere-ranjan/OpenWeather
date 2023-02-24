package com.ranjan.openweather.di

import com.ranjan.openweather.BuildConfig
import com.ranjan.openweather.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttp(
        httpInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okhttp.addInterceptor(httpInterceptor)
        }
        return okhttp.build()
    }


    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService =
        retrofitBuilder.build().create(ApiService::class.java)
}