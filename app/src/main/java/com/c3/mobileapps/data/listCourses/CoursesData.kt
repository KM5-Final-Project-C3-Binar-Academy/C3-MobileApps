package com.c3.mobileapps.data.listCourses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoursesData(
    val author: String?,
    val code: String?,
    @SerializedName("course_category")
    val courseCategory: CourseCategory?,
    @SerializedName("course_category_id")
    val courseCategoryId: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val description: String?,
    val difficulty: String?,
    val id: String?,
    val image: String?,
    @SerializedName("intro_video")
    val introVideo: String?,
    val name: String?,
    @SerializedName("onboarding_text")
    val onboardingText: String?,
    val premium: Boolean?,
    val price: Int?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("target_audience")
    val targetAudience: List<String?>?,
    val telegram: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val user: User?,
    @SerializedName("user_id")
    val userId: String?
):Parcelable