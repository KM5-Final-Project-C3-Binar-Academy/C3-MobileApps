package com.c3.mobileapps.data.remote.model.response.course


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)