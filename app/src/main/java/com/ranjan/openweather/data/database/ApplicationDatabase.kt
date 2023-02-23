package com.ranjan.openweather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ranjan.openweather.BuildConfig
import com.ranjan.openweather.data.entities.User
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getDao(): UsersDao

    companion object {
        fun getInstance(
            context: Context, passcode: String = BuildConfig.DATABASE_KEY
        ): ApplicationDatabase {
            val supportFactory = SupportFactory(SQLiteDatabase.getBytes(passcode.toCharArray()))
            return Room.databaseBuilder(
                context, ApplicationDatabase::class.java, name = "User_table"
            ).openHelperFactory(supportFactory).fallbackToDestructiveMigration().build()
        }
    }
}