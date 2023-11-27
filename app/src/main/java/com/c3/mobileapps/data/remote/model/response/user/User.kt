package com.c3.mobileapps.data.remote.model.response.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("admin")
    var admin: Boolean?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("phone_number")
    var phoneNumber: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)