package com.c3.mobileapps.data.remote.model.response.notification


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("user_id")
    var userId: String?,
    @SerializedName("viewed")
    var viewed: Boolean?
)