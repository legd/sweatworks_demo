package com.example.sweatworksdemo.api

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object ApiCallerBuilder {

//    private val client = OkHttpClient.Builder().build()
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