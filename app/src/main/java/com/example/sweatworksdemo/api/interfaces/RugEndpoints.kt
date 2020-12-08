package com.example.sweatworksdemo.api.interfaces

import com.example.sweatworksdemo.api.model.RandomUserGenerator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RugEndpoints {

    /**
     * Method to get all the users.
     *
     * @return UserList object containing a list of users
     */
    @GET("api/")
    fun getUsers(@Query("results") total: Int) : Call<RandomUserGenerator>
}