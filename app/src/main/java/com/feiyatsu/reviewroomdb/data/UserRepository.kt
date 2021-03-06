package com.feiyatsu.reviewroomdb.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class UserRepository @Inject
constructor(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) = userDao.addUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    suspend fun deleteAllData() = userDao.deleteAllData()
}