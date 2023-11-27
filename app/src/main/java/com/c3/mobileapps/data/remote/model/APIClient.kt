package com.c3.mobileapps.data.remote.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://api-binar-backend.risalamin.com/"

    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(interceptor)

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    var instance = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build()

    val endpointAPIService = instance.create(APIService::class.java)
}