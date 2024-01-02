package com.c3.mobileapps.data.remote.model.response.notification


import com.google.gson.annotations.SerializedName

data class NotificationIdResponse(
    @SerializedName("data")
    var `data`: Notification?,
    @SerializedName("message")
    var message: String?
)