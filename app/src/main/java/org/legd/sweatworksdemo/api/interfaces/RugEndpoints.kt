package org.legd.sweatworksdemo.api.interfaces

import org.legd.sweatworksdemo.api.model.RandomUserGenerator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface declaring all the available endpoints to call.
 */
interface RugEndpoints {

    /**
     * Method to get all the users.
     *
     * @return UserList object containing a list of users
     */
    @GET("api/")
    fun getUsers(@Query("results") total: Int) : Call<RandomUserGenerator>
}