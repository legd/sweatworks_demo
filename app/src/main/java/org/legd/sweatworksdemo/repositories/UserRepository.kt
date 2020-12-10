package org.legd.sweatworksdemo.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import org.legd.sweatworksdemo.api.ApiCallerBuilder
import org.legd.sweatworksdemo.api.interfaces.RugEndpoints
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.api.model.RandomUserGenerator
import org.legd.sweatworksdemo.database.dao.UserDao
import org.legd.sweatworksdemo.database.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class in charge to manage the users data sources of the app.
 */
class UserRepository(private val userDao: UserDao) {

    val users: Flow<List<User>> = userDao.getAllUsers()

    /**
     * Insert the given user in the database.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    /**
     * Returns users with the matching name from the database.
     */
    fun searchFavoriteUser(name: String): LiveData<List<User>> {
        return userDao.getUsersByName(name).asLiveData()
    }

    /**
     * Returns a list of users from the API.
     */
    fun getApiUsers(): MutableLiveData<List<ApiUser>> {

        val apiUsers: MutableLiveData<List<ApiUser>> = MutableLiveData()
        val request = ApiCallerBuilder.buildService(RugEndpoints::class.java)
        val call = request.getUsers(50)

        call.enqueue(object : Callback<RandomUserGenerator> {

            override fun onResponse(call: Call<RandomUserGenerator>, response: Response<RandomUserGenerator>) {
                Log.d("TAG", "onResponse: " + response.code())
                Log.d("TAG", "onResponse: " + response.body())
                apiUsers.postValue(response.body()?.results)
            }

            override fun onFailure(call: Call<RandomUserGenerator>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
            }
        })

        return apiUsers
    }
}