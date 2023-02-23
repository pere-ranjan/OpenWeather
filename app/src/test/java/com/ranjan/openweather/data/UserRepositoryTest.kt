package com.ranjan.openweather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ranjan.openweather.common.LoginResponse
import com.ranjan.openweather.common.RegisterResponse
import com.ranjan.openweather.data.database.DummyUserRepository
import com.ranjan.openweather.data.database.UsersDao
import com.ranjan.openweather.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var userRepo: UserRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var userDao: UsersDao

    @Before
    fun setUp() {
        userDao = DummyUserRepository()
        userRepo = UserRepositoryImpl(userDao)
    }

    @Test
    fun `user register successfully`() {
        val name = "ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        runBlocking {
            val response: Flow<RegisterResponse> = userRepo.register(name, email, password)
            assertEquals(RegisterResponse.SUCCESS, response.first())
        }
    }

    @Test
    fun `user register twice with same email`() {
        val name = "ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"

        runBlocking {
            userRepo.register(name, email, password).first()
            val response = userRepo.register(name, email, password)
            assertEquals(RegisterResponse.FAILED, response.first())
        }
    }

    @Test
    fun `user register and try to login return LoggedIn`() {
        val name = "ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        runBlocking {
            userRepo.register(name, email, password).first()
            val loginResponse = userRepo.login(email, password)
            assertEquals(LoginResponse.LoggedIn, loginResponse.first())
        }
    }

    @Test
    fun `user register and try to login with wrong password return Wrong Password`() {
        val name = "ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        runBlocking {
            userRepo.register(name, email, password).first()
            val loginResponse = userRepo.login(email, password + "extra")
            assertEquals(LoginResponse.WrongPassword, loginResponse.first())
        }
    }

    @Test
    fun `user not register and try to login return Email address not found`() {
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        runBlocking {
            val loginResponse = userRepo.login(email, password)
            assertEquals(LoginResponse.EmailNotFound, loginResponse.first())
        }
    }

}