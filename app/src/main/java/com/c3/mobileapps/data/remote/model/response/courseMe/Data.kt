package com.c3.mobileapps.data.remote.model.response.courseMe

data class Data(
    val author: String?,
    val code: String?,
    val course_category: CourseCategory?,
    val course_category_id: String?,
    val course_chapter: List<CourseChapter?>?,
    val created_at: String?,
    val description: String?,
    val difficulty: String?,
    val id: String?,
    val image: Any?,
    val intro_video: String?,
    val name: String?,
    val onboarding_text: String?,
    val premium: Boolean?,
    val price: Int?,
    val rating: String?,
    val target_audience: List<String?>?,
    val telegram: String?,
    val total_duration: Int?,
    val total_materials: Int?,
    val updated_at: String?,
    val user_id: String?
)