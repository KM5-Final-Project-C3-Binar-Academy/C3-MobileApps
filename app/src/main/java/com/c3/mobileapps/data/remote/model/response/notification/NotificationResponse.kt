package com.c3.mobileapps.data.remote.model.response.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data")
    var `data`: List<Notification>
)