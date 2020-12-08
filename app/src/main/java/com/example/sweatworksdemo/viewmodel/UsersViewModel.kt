package com.example.sweatworksdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sweatworksdemo.api.model.ApiUser
import com.example.sweatworksdemo.repositories.UserRepository

class UsersViewModel: ViewModel() {

    fun getUsers(): LiveData<List<ApiUser>> {
        val users = UserRepository.getUsers()
        return users
    }
}
