package com.ranjan.openweather.data

import com.ranjan.openweather.common.LoginResponse
import com.ranjan.openweather.common.RegisterResponse
import com.ranjan.openweather.data.database.UsersDao
import com.ranjan.openweather.data.database.entities.User
import com.ranjan.openweather.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UsersDao
) : UserRepository {

    override fun register(
        name: String, email: String, password: String
    ): Flow<RegisterResponse> {
        return flow {
            dao.registerUser(User(name, email, password))
            emit(RegisterResponse.SUCCESS)
        }.catch {
            emit(RegisterResponse.FAILED)
        }.flowOn(Dispatchers.IO)
    }


    override fun login(email: String, password: String): Flow<LoginResponse> {
        return flow {
            if (dao.userExits(email)) {
                val user = dao.loginUser(email, password)
                if (user != null) emit(LoginResponse.LoggedIn)
                else emit(LoginResponse.WrongPassword)
            } else emit(LoginResponse.EmailNotFound)
        }.flowOn(Dispatchers.IO)
    }
}