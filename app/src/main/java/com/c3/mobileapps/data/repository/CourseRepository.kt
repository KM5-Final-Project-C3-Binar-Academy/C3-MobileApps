package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.response.updateCourseMaterial.DataMaterialStatus
import com.c3.mobileapps.data.remote.service.ApiServiceCourse

class
CourseRepository(private val apiServiceCourse: ApiServiceCourse) {
    suspend fun getCategory() = apiServiceCourse.getCategory()

    suspend fun getCourseId(id: String?) = apiServiceCourse.getCourseById(id)

    suspend fun getCourseUser(token: String, type: String?) =
        apiServiceCourse.getCourseUser(token, type)

    suspend fun getCourseUserMaterial(token: String, id: String?) =
        apiServiceCourse.getCourseByIdUser(token, id)

    suspend fun getCourse(
        type: String?,
        filter: String?,
        category: String?,
        search: String?,
        difficulty: String?
    ) = apiServiceCourse.getCourse(type, filter, category, search, difficulty)

    suspend fun updateCourseMaterial (token: String, id: String?)=
        apiServiceCourse.updateCoursesMaterial(token, id)
}