package com.ranjan.openweather.data.database

import com.ranjan.openweather.data.entities.User


class DummyUserRepository : UsersDao {
    private val list = ArrayList<User>()

    override suspend fun registerUser(user: User) {
        if (!userExits(user.email)) {
            list.add(user)
        } else throw java.lang.Exception()
    }

    override suspend fun userExits(email: String): Boolean {
        val user = list.find { it.email == email }
        return user != null
    }

    override suspend fun loginUser(email: String, password: String): User? {
        if (userExits(email)) {
            return list.find { it.email == email && it.password == password }
        }
        return null
    }
}