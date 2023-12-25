package com.c3.mobileapps.data.remote.model.response.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseMaterialStatus(
    @SerializedName("completed")
    var completed: Boolean?,
    @SerializedName("course_ma")
    var courseMa: String?,
    @SerializedName("created_a")
    var createdA: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("updated_a")
    var updatedA: String?,
    @SerializedName("user_id")
    var userId: String?
): Parcelable