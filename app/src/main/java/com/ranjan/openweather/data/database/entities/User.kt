package com.ranjan.openweather.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Users", indices = [Index(value = ["email"], unique = true)])
data class User(val fullName: String, val email: String, val password: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
