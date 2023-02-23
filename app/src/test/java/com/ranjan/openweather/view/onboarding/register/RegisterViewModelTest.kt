package com.ranjan.openweather.view.onboarding.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ranjan.openweather.R
import com.ranjan.openweather.common.RegisterResponse
import com.ranjan.openweather.domain.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = newSingleThreadContext("SignUp Thread")

    @Mock
    private lateinit var userRepo: UserRepository

    private lateinit var viewModel: RegisterViewModel


    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
        viewModel = RegisterViewModel(userRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.close()
    }

    @Test
    fun `all filed empty return error in all text fields`() {
        viewModel.registerUser()
        assertEquals(R.string.defaultErrorText, viewModel.nameError.value)
        assertEquals(R.string.defaultErrorText, viewModel.emailError.value)
        assertEquals(R.string.defaultErrorText, viewModel.passwordError.value)
        assertEquals(R.string.defaultErrorText, viewModel.rePasswordError.value)
    }

    @Test
    fun `emptyName return error`() {
        viewModel.mName.value = ""
        viewModel.registerUser()
        assertEquals(R.string.defaultErrorText, viewModel.nameError.value)
    }

    @Test
    fun `name length less than 3 give NameTooShort`() {
        viewModel.mName.value = "a"
        viewModel.registerUser()
        assertEquals(R.string.name_too_short, viewModel.nameError.value)
    }

    @Test
    fun `valid name not return error`() {
        viewModel.mName.value = "Ranjan"
        viewModel.registerUser()
        assertEquals(null, viewModel.nameError.value)
    }

    @Test
    fun `empty email return error`() {
        viewModel.mEmail.value = ""
        viewModel.registerUser()
        assertEquals(R.string.defaultErrorText, viewModel.emailError.value)
    }

    @Test
    fun `not valid Email return error`() {
        viewModel.mEmail.value = "abcdgmail.com"
        viewModel.registerUser()
        assertEquals(R.string.email_not_valid, viewModel.emailError.value)
    }


    @Test
    fun `valid Email not return error`() {
        viewModel.mEmail.value = "abcd@gmail.com"
        viewModel.registerUser()
        assertEquals(null, viewModel.emailError.value)
    }


    @Test
    fun `not valid Password return password too short`() {
        viewModel.mPassword.value = "Pass123"
        viewModel.registerUser()
        assertEquals(R.string.password_too_short, viewModel.passwordError.value)
    }


    @Test
    fun `valid Password not return error`() {
        viewModel.mPassword.value = "Pass@123"
        viewModel.registerUser()
        assertEquals(null, viewModel.passwordError.value)
    }

    @Test
    fun `re-password filed empty return error`() {
        viewModel.registerUser()
        assertEquals(R.string.defaultErrorText, viewModel.rePasswordError.value)
    }

    @Test
    fun `re-password and password not match return repassword not match`() {
        setUpValues(password = "Pass@1234", rePassword = "Pass@123")

        viewModel.registerUser()
        assertEquals(R.string.password_do_not_match, viewModel.rePasswordError.value)
    }

    @Test
    fun `re-password and password match return no error`() {
        setUpValues(password = "Pass@1234", rePassword = "Pass@1234")
        viewModel.registerUser()
        assertEquals(null, viewModel.rePasswordError.value)
    }

    @Test
    fun `all valid fields return null to all fields`() {
        setUpValues(
            name = "Ranjan",
            email = "abcd@gmail.com",
            password = "Pass@1234",
            rePassword = "Pass@1234"
        )

        assertEquals(null, viewModel.nameError.value)
        assertEquals(null, viewModel.emailError.value)
        assertEquals(null, viewModel.passwordError.value)
        assertEquals(null, viewModel.rePasswordError.value)
    }

    @Test
    fun `valid registration return registerResponse as true `() {
        val name = "Ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        val registerResponse = flow {
            emit(RegisterResponse.SUCCESS)
        }
        Mockito.`when`(userRepo.register(name, email, password)).thenReturn(registerResponse)

        setUpValues(name, email, password, password)
        viewModel.registerUser()

        runBlocking(dispatcher) {
            assertEquals(true, viewModel.registerResponse.value)
        }
    }

    @Test
    fun `register user with same email twice return email aleardy exits `() {
        val name = "Ranjan"
        val email = "ranjan@gmail.com"
        val password = "Pass@123"
        val registerResponse = flow {
            emit(RegisterResponse.FAILED)
        }
        Mockito.`when`(userRepo.register(name, email, password)).thenReturn(registerResponse)

        setUpValues(name, email, password, password)

        viewModel.registerUser()

        runBlocking(dispatcher) {
            assertEquals(R.string.email_already_in_use, viewModel.emailError.value)
        }
    }

    private fun setUpValues(
        name: String = "", email: String = "", password: String = "", rePassword: String = ""
    ) {
        viewModel.mName.value = name
        viewModel.mEmail.value = email
        viewModel.mPassword.value = password
        viewModel.mRePassword.value = rePassword
    }
}