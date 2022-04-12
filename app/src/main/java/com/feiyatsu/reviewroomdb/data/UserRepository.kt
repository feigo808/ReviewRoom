package com.feiyatsu.reviewroomdb.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class UserRepository @Inject
constructor(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) = userDao.addUser(user)
}