package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.service.ApiServiceCourse

class CourseRepository(private val apiServiceCourse: ApiServiceCourse) {
    suspend fun getCategory() = apiServiceCourse.getCategory()
    suspend fun getCourse(
        type: String?,
        filter: String?,
        category: String?,
        search: String?,
        difficulty: String?
    ) = apiServiceCourse.getCourse(type, filter, category, search, difficulty)
}