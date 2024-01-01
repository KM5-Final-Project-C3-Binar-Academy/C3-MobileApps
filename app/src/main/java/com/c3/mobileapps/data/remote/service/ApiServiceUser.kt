package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.request.user.EditPassword
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.model.response.user.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiServiceUser {
    @GET("users/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): AuthResponse

    @Multipart
    @PUT("users/me")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part image: MultipartBody.Part
    ): AuthResponse

    @PUT("/users/me/password-reset")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body updatedField: EditPassword
    ): Response<AuthResponse>

}