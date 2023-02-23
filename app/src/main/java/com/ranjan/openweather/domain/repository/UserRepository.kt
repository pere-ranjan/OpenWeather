package com.ranjan.openweather.domain.repository

import com.ranjan.openweather.common.LoginResponse
import com.ranjan.openweather.common.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun register(name: String, email: String, password: String): Flow<RegisterResponse>

    fun login(email: String, password: String): Flow<LoginResponse>

}