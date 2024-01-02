package com.c3.mobileapps.data.remote.model.response.payment


import android.os.Parcelable
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    @SerializedName("course")
    var course: Course?,
    @SerializedName("course_id")
    var courseId: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("expired_at")
    var expiredAt: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("paid_at")
    var paidAt: String?,
    @SerializedName("payment_method")
    var paymentMethod: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("user_id")
    var userId: String?
): Parcelable