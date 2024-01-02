package com.c3.mobileapps.data.remote.model.response.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseChapter(
    @SerializedName("course_id")
    var courseId: String?,
    @SerializedName("course_material")
    var courseMaterial: List<CourseMaterial?>?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("duration")
    var duration: Int?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("order_index")
    var orderIndex: Int?,
    @SerializedName("updated_at")
    var updatedAt: String?
) :Parcelable