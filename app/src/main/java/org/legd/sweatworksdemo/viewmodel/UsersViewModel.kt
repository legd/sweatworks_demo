package org.legd.sweatworksdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.repositories.UserRepository

class UsersViewModel: ViewModel() {

    fun getUsers(): LiveData<List<ApiUser>> {
        return UserRepository.getApiUsers()
    }
}
