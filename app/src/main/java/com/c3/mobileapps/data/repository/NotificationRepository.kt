package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.service.ApiServiceNotification

class NotificationRepository(private val apiServiceNotification: ApiServiceNotification) {

    suspend fun getNotification(token:String) = apiServiceNotification.getNotification(token)

    suspend fun readAllNotif(token: String) = apiServiceNotification.readAllNotification(token)

    suspend fun updateNotif(token: String, id:String) = apiServiceNotification.updateNotification(token,id)

    suspend fun deleteNotif(token: String, id:String) = apiServiceNotification.deleteNotification(token,id)
}