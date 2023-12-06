package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceCourse {
    @GET("course-categories")
    suspend fun getCategory(): CategoryResponse

    @GET("courses")
    suspend fun getCourse(
        @Query("type") type: String?,
        @Query("filter") filter: String?,
        @Query("category") category: String?,
        @Query("search") search: String?,
        @Query("difficulty") difficulty: String?,
    ): CourseResponse
}