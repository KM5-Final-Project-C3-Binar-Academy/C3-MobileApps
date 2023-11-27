package com.c3.mobileapps.data.remote.model.response.course


import com.c3.mobileapps.data.remote.model.response.user.User
import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("author")
    var author: String?,
    @SerializedName("code")
    var code: String?,
    @SerializedName("course_category")
    var courseCategory: Category?,
    @SerializedName("course_category_id")
    var courseCategoryId: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("difficulty")
    var difficulty: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("intro_video")
    var introVideo: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("onboarding_text")
    var onboardingText: String?,
    @SerializedName("premium")
    var premium: Boolean?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("rating")
    var rating: String?,
    @SerializedName("target_audience")
    var targetAudience: List<String?>?,
    @SerializedName("telegram")
    var telegram: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?,
    @SerializedName("user")
    var user: User?,
    @SerializedName("user_id")
    var userId: String?
)