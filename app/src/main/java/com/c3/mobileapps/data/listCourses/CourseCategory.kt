package com.c3.mobileapps.data.listCourses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseCategory(
    @SerializedName("created_at")
    val createdAt: String?,
    val id: String?,
    val image: String?,
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
): Parcelable