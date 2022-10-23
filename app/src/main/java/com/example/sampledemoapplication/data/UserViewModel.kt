package com.example.sampledemoapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val loginDao = UserDatabase.getDatabase(application).userDuo()
        repository = UserRepository(loginDao)
        readAllData = repository.readAllData
    }

    fun getUser(i: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.display(i)
    }

    fun addUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(user)
    }

}