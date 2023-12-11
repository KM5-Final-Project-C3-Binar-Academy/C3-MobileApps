package com.c3.mobileapps.data.remote.model.response.CourseId

data class CourseChapter(
    val course_id: String?,
    val course_material: List<CourseMaterial?>?,
    val course_material_id: String?,
    val created_at: String?,
    val duration: Int?,
    val id: String?,
    val name: String?,
    val order_index: Int?,
    val updated_at: String?
)