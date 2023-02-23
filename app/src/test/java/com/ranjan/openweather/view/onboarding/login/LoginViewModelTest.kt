package com.ranjan.openweather.view.onboarding.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ranjan.openweather.R
import com.ranjan.openweather.common.LoginResponse
import com.ranjan.openweather.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = newSingleThreadContext("SignIn Thread")

    @Mock
    private lateinit var userRepo: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(userRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.close()
    }


    @Test
    fun `empty email return defaultErrorText`() {
        viewModel.loginUser()
        assertEquals(R.string.defaultErrorText, viewModel.passwordError.value)
    }

    @Test
    fun `invalid email return defaultErrorText`() {
        setUpValues(email = "test@@domain.com")
        viewModel.loginUser()
        assertEquals(R.string.email_not_valid, viewModel.emailError.value)
    }

    @Test
    fun `valid email return email error null`() {
        setUpValues(email = "test@domain.com")
        viewModel.loginUser()
        Assert.assertEquals(null, viewModel.emailError.value)
    }

    @Test
    fun `empty password return defaultErrorText`() {
        viewModel.loginUser()
        assertEquals(R.string.defaultErrorText, viewModel.passwordError.value)
    }

    @Test
    fun `invalid  password return error`() {
        viewModel.loginUser()
        assertEquals(
            R.string.defaultErrorText, viewModel.passwordError.value
        )
    }

    @Test
    fun `valid email and valid password`() {
        setUpValues(email = "test@domain.com", password = "Pass@123")
        viewModel.loginUser()
        Assert.assertEquals(null, viewModel.emailError.value)
        Assert.assertEquals(null, viewModel.passwordError.value)
    }

    @Test
    fun `valid email and valid password go to userRepo login method`() {
        setUpValues(email = "test@domain.com", password = "Pass@123")
        viewModel.loginUser()
        runBlocking(dispatcher) {
            Mockito.verify(userRepo, Mockito.times(1))
                .login(viewModel.mEmail.value, viewModel.mPassword.value)
        }
    }


    @Test
    fun `wrong password return wrong password error`() {
        val email = "test@domain.com"
        val password = "Pass@123"
        setUpValues(email, password)
        val loginResponse = flow { emit(LoginResponse.WrongPassword) }
        Mockito.`when`(userRepo.login(email, password)).thenReturn(loginResponse)

        viewModel.loginUser()
        runBlocking(dispatcher) {
            assertEquals(LoginResponse.WrongPassword.value, viewModel.passwordError.value)
        }
    }

    @Test
    fun `email not exits return email error`() {
        val email = "test@domain.com"
        val password = "Pass@123"
        setUpValues(email, password)
        val loginResponse = flow { emit(LoginResponse.EmailNotFound) }
        Mockito.`when`(userRepo.login(email, password)).thenReturn(loginResponse)

        viewModel.loginUser()
        runBlocking(dispatcher) {
            assertEquals(LoginResponse.EmailNotFound.value, viewModel.emailError.value)
        }
    }


    @Test
    fun `valid email and password return login response true`() {
        val email = "test@domain.com"
        val password = "Pass@123"
        setUpValues(email, password)
        val loginResponse = flow { emit(LoginResponse.LoggedIn) }
        Mockito.`when`(userRepo.login(email, password)).thenReturn(loginResponse)

        viewModel.loginUser()
        runBlocking(dispatcher) {
            assertEquals(true, viewModel.loginResponse.value)
        }
    }

    private fun setUpValues(email: String = "", password: String = "") {
        viewModel.mEmail.value = email
        viewModel.mPassword.value = password
    }
}