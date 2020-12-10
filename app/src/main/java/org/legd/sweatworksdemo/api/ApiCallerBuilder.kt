package org.legd.sweatworksdemo.api

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Class for wrapping the initialization of the Retrofit and RugEndpoints objects, using a
 * singleton.
 */
object ApiCallerBuilder {

    private val client = getHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()

    fun getHttpClient(): OkHttpClient.Builder {
//        val log = HttpLoggingInterceptor()
//        log.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS))
//                .addInterceptor(log)
    }

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}