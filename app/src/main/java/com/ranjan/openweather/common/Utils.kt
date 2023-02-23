package com.ranjan.openweather.common

val emailPattern =
    buildString { append("^([_a-zA-Z\\d-]+(\\.[_a-zA-Z\\d-]+)*@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d-]+)*(\\.[a-zA-Z]{1,6}))?$") }.toRegex()
