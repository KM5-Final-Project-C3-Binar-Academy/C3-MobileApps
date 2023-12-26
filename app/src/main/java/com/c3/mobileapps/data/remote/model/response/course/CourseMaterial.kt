package com.c3.mobileapps.data.remote.model.response.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseMaterial(
    @SerializedName("course_chapter_id")
    var courseChapterId: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("order_index")
    var orderIndex: Int?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("video")
    var video: String?,
    @SerializedName("course_material_status")
    var courseMaterialStatus: List<CourseMaterialStatus?>?
): Parcelable