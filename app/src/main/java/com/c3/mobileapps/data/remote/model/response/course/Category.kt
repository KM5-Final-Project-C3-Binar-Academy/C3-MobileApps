package com.c3.mobileapps.data.remote.model.response.course


import android.os.Parcelable
import com.c3.mobileapps.data.local.model.CategoryLocal
import com.c3.mobileapps.data.remote.model.response.user.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("updated_at")
    var updatedAt: String?
): Parcelable

fun Category.toDomain() = CategoryLocal(id = id?.toInt(), name = name, image=image)