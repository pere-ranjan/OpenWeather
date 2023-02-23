package com.ranjan.openweather.view.onboarding.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranjan.openweather.R
import com.ranjan.openweather.common.LoginResponse
import com.ranjan.openweather.common.emailPattern
import com.ranjan.openweather.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    private val _emailError = MutableStateFlow<Int?>(null)
    val emailError get() = _emailError.asStateFlow()
    private val _passwordError = MutableStateFlow<Int?>(null)
    val passwordError get() = _passwordError.asStateFlow()

    val mEmail = MutableStateFlow("")
    val mPassword = MutableStateFlow("")

    private val _loginResponse = MutableStateFlow(false)
    val loginResponse = _loginResponse.asStateFlow()
    fun loginUser() {
        _emailError.value = null
        _passwordError.value = null
        val email = mEmail.value
        val password = mPassword.value

        if (email.isBlank()) _emailError.value = R.string.defaultErrorText
        else if (!emailPattern.matches(email)) {
            _emailError.value = R.string.email_not_valid
            return
        }
        if (password.isBlank()) _passwordError.value = R.string.defaultErrorText

        if (_emailError.value == null && _passwordError.value == null) {
            viewModelScope.launch {
                repo.login(email, password).collect {loginResponse->
                    when (loginResponse) {
                        LoginResponse.LoggedIn -> _loginResponse.value = true
                        LoginResponse.WrongPassword -> _passwordError.value = loginResponse.value
                        LoginResponse.EmailNotFound -> _emailError.value = loginResponse.value
                    }
                }
            }
        }
    }
}