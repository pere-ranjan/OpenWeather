package com.ranjan.openweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ranjan.openweather.data.entities.User

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

    @Query("SELECT EXISTS(SELECT * FROM USERS where email= :email)")
    suspend fun userExits(email: String): Boolean

    @Query("Select * from USERS where email= :email AND password= :password")
    suspend fun loginUser(email: String, password: String): User?

}