package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseIdResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceCourse {
    @GET("course-categories")
    suspend fun getCategory(): CategoryResponse

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: String?) : CourseIdResponse

    @GET("courses/me")
    suspend fun getCourseUser(
        @Header("Authorization") token: String,
    ): CourseResponse

    @GET("courses")
    suspend fun getCourse(
        @Query("type") type: String?,
        @Query("filter") filter: String?,
        @Query("category") category: String?,
        @Query("search") search: String?,
        @Query("difficulty") difficulty: String?,
    ): CourseResponse
}