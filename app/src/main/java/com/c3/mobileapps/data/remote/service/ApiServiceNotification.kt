package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.response.notification.NotificationResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceNotification {

    @GET("user-notifications")
    suspend fun getNotification(
        @Header("Authorization") token: String,
    ): NotificationResponse

    @POST("user-notifications/read-all")
    suspend fun readAllNotification(
        @Header("Authorization") token: String,
    ): NotificationResponse

    @PUT("user-notifications/{id}")
    suspend fun updateNotification(
        @Header("Authorization") token: String,
        @Path("id") id: String?
    ): NotificationResponse

    @DELETE("user-notifications/{id}")
    suspend fun deleteNotification(
        @Header("Authorization") token: String,
        @Path("id") id: String?
    ): NotificationResponse
}