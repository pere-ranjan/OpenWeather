package com.ranjan.openweather.common

import androidx.annotation.StringRes
import com.ranjan.openweather.R

enum class LoginResponse(@StringRes val value: Int) {
    LoggedIn(R.string.logged_in),
    WrongPassword(R.string.wrong_password),
    EmailNotFound(R.string.email_not_found)
}

enum class RegisterResponse(@StringRes val value: Int) {
    SUCCESS(R.string.success),
    FAILED(R.string.failed)
}