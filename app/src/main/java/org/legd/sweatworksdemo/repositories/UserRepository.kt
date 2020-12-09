package org.legd.sweatworksdemo.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.legd.sweatworksdemo.api.ApiCallerBuilder
import org.legd.sweatworksdemo.api.interfaces.RugEndpoints
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.api.model.RandomUserGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun getApiUsers(): MutableLiveData<List<ApiUser>> {

        val users: MutableLiveData<List<ApiUser>> = MutableLiveData()
        val request = ApiCallerBuilder.buildService(RugEndpoints::class.java)
        val call = request.getUsers(50)

        call.enqueue(object : Callback<RandomUserGenerator> {

            override fun onResponse(call: Call<RandomUserGenerator>, response: Response<RandomUserGenerator>) {
                Log.d("TAG", "onResponse: " + response.code())
                Log.d("TAG", "onResponse: " + response.body())

//                if (response.body()?.results?.size?. > 0) {
//
//                }
//                for (user in response.body()?.results!!) {
//                    user
//                }

                users.postValue(response.body()?.results)
            }

            override fun onFailure(call: Call<RandomUserGenerator>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
            }
        })

        return users
    }
}