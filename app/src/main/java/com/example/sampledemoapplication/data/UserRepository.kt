package com.example.sampledemoapplication.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    fun display(i: Int) {
        userDao.getuser(i)
    }

    suspend fun insert(user: User) {
        userDao.addLoginUser(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    val readAllUserData: LiveData<List<UserInfo>> = userDao.readAllUserData()

    fun displayUserInfo(i: Int) {
        userDao.getuserInfo(i)
    }

    suspend fun insertUserInfo(userInfo: UserInfo) {
        userDao.addUserInfo(userInfo)
    }

    suspend fun deleteUserInfo(userInfo: UserInfo) {
        userDao.deleteInfo(userInfo)
    }

    suspend fun updateUserInfo(userInfo: UserInfo) {
        userDao.updateInfo(userInfo)
    }
}