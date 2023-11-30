package com.c3.mobileapps.data.remote

import com.c3.mobileapps.data.remote.service.ApiServiceCourse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api-binar-backend.risalamin.com/"

    fun setRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun setApiServiceCourse(retrofit: Retrofit): ApiServiceCourse =
        retrofit.create(ApiServiceCourse::class.java)
}