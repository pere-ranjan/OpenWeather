package com.ranjan.openweather.common

import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setErrorText(view: TextInputLayout, @StringRes strRes: Int?) {
    view.error = if (strRes == null) null else view.context.getString(strRes)
}

@BindingAdapter("loadImage")
fun ImageView.loadIconImage(url: String?) {
    Glide.with(this).load(url).into(this)
}