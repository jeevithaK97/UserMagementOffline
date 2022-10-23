package com.example.sampledemoapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoViewModel (application: Application) : AndroidViewModel(application) {

    val readAllUserData: LiveData<List<UserInfo>>
    private val repository: UserRepository

    init {
        val userInfoDao = UserDatabase.getDatabase(application).userDuo()
        repository = UserRepository(userInfoDao)
        readAllUserData = repository.readAllUserData
    }

    fun getUserInfo(i: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.displayUserInfo(i)
    }

    fun addUserInfo(userInfo: UserInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUserInfo(userInfo)
    }

    fun updateUserInfo(userInfo: UserInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUserInfo(userInfo)
    }

    fun deleteUseInfo(userInfo: UserInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUserInfo(userInfo)
    }

}