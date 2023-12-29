package com.c3.mobileapps.data.remote

import com.c3.mobileapps.data.remote.service.ApiServiceAuth
import com.c3.mobileapps.data.remote.service.ApiServiceCourse
import com.c3.mobileapps.data.remote.service.ApiServiceNotification
import com.c3.mobileapps.data.remote.service.ApiServicePayment
import com.c3.mobileapps.data.remote.service.ApiServiceUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    fun setApiServiceAuth(retrofit: Retrofit): ApiServiceAuth =
        retrofit.create(ApiServiceAuth::class.java)
<<<<<<< Updated upstream

    fun setApiServicePayment(retrofit: Retrofit): ApiServicePayment =
        retrofit.create(ApiServicePayment::class.java)

    fun setApiServiceUser(retrofit: Retrofit): ApiServiceUser =
        retrofit.create(ApiServiceUser::class.java)

    fun setApiServiceNotification(retrofit: Retrofit): ApiServiceNotification =
        retrofit.create(ApiServiceNotification::class.java)
=======
>>>>>>> Stashed changes
}