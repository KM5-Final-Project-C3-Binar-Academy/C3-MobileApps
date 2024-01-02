package com.c3.mobileapps.data.remote.model.response.user


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    var `data`: User?,
    @SerializedName("message")
    var message: String?
)