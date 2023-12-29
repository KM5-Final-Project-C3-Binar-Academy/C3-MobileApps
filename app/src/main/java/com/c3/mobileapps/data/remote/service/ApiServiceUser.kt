package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.model.response.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServiceUser {
    @GET("users/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): AuthResponse

    @PUT("users/me")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body updatedField: EditUser
    ): AuthResponse

    //ubah password belum

}