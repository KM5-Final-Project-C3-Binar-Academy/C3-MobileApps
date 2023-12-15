package com.c3.mobileapps.data.remote

import com.c3.mobileapps.data.remote.service.ApiServiceCourse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

object ApiClient {
    private const val BASE_URL = "https://api.belajar.risalamin.com/"

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

    fun getVideoUrl(apiurl : String, callback: okhttp3.Callback){
        val request = Request.Builder()
            .url(apiurl)
            .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(callback)
    }
}