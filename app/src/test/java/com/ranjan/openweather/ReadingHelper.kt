package com.ranjan.openweather

import java.io.InputStreamReader

object ReadingHelper {

    fun readFileResource(fileName: String): String {
        val inputStream = ReadingHelper::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}