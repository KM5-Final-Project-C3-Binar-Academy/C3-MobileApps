package com.c3.mobileapps.data.remote.model.response.user


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("admin")
    var admin: Boolean?,
    @SerializedName("created_at")
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
    @SerializedName("updated_at")
    var updatedAt: String?
): Parcelable