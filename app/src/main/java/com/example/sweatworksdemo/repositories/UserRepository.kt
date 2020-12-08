package com.example.sweatworksdemo.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sweatworksdemo.api.ApiCallerBuilder
import com.example.sweatworksdemo.api.interfaces.RugEndpoints
import com.example.sweatworksdemo.api.model.ApiUser
import com.example.sweatworksdemo.api.model.RandomUserGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun getUsers(): MutableLiveData<List<ApiUser>> {

        val users: MutableLiveData<List<ApiUser>> = MutableLiveData()
        val request = ApiCallerBuilder.buildService(RugEndpoints::class.java)
        val call = request.getUsers(5)

        call.enqueue(object : Callback<RandomUserGenerator> {

            override fun onResponse(call: Call<RandomUserGenerator>, response: Response<RandomUserGenerator>) {
                Log.i("TAG", "onResponse: " + response.body())

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