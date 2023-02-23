package com.ranjan.openweather.view.onboarding.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranjan.openweather.R
import com.ranjan.openweather.common.RegisterResponse
import com.ranjan.openweather.common.emailPattern
import com.ranjan.openweather.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

    private val _nameError = MutableStateFlow<Int?>(null)
    val nameError get() = _nameError.asStateFlow()
    private val _emailError = MutableStateFlow<Int?>(null)
    val emailError get() = _emailError.asStateFlow()
    private val _passwordError = MutableStateFlow<Int?>(null)
    val passwordError get() = _passwordError.asStateFlow()
    private val _rePasswordError = MutableStateFlow<Int?>(null)
    val rePasswordError get() = _rePasswordError.asStateFlow()

    val mName = MutableStateFlow("")
    val mEmail = MutableStateFlow("")
    val mPassword = MutableStateFlow("")
    val mRePassword = MutableStateFlow("")

    private val _registerResponse = MutableStateFlow(false)
    val registerResponse = _registerResponse.asStateFlow()
    fun registerUser() {
        _nameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _rePasswordError.value = null

        val name = mName.value
        val email = mEmail.value
        val password = mPassword.value
        val rePassword = mRePassword.value

        if (name.isBlank()) _nameError.value = R.string.defaultErrorText
        else if (name.length < 3) _nameError.value = R.string.name_too_short

        if (email.isBlank()) _emailError.value = R.string.defaultErrorText
        else if (!emailPattern.matches(email)) _emailError.value = R.string.email_not_valid

        if (password.isBlank()) _passwordError.value = R.string.defaultErrorText
        else if (password.length < 8) _passwordError.value = R.string.password_too_short

        if (rePassword.isBlank()) _rePasswordError.value = R.string.defaultErrorText
        else if (rePassword != password) _rePasswordError.value = R.string.password_do_not_match

        if (_emailError.value == null && _nameError.value == null && _passwordError.value == null && _rePasswordError.value == null) {
            viewModelScope.launch {
                userRepo.register(name, email, password).collect {
                    if (it == RegisterResponse.FAILED) _emailError.value =
                        R.string.email_already_in_use
                    else _registerResponse.value = true
                }
            }
        }
    }

}