package com.c3.mobileapps.data.listCourses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val admin: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
    val email: String?,
    val id: String?,
    val name: String?,
    val password: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
):Parcelable