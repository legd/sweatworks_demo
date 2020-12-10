package org.legd.sweatworksdemo.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.database.models.User
import org.legd.sweatworksdemo.repositories.UserRepository
import java.lang.IllegalArgumentException

/**
 * ViewModel class to manage the interactions between the UI and user repository.
 */
class UsersViewModel(private val repository: UserRepository): ViewModel() {

    val favoriteUsers: LiveData<List<User>> = repository.users.asLiveData()
    val apiUsers: LiveData<List<ApiUser>> = repository.getApiUsers()

    /**
     * Returns a user list with the matching names.
     */
    fun searchUser(name: String) : LiveData<List<User>> {
        return repository.searchFavoriteUser(name)
    }

    /**
     * Insert a single user to the database.
     */
    fun saveUserAsFavorite(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
}

/**
 * Class for the ViewModelProvider factory pattern.
 */
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
