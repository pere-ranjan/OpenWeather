package com.ranjan.openweather.common

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setErrorText(view: TextInputLayout, @StringRes strRes: Int?) {
    view.error = if (strRes == null) null else view.context.getString(strRes)
}
