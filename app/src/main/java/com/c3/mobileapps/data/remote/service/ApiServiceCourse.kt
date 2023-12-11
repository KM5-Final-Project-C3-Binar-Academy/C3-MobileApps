package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.response.CourseId.CourseIdResponse
import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceCourse {
    @GET("course-categories")
    suspend fun getCategory(): CategoryResponse

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: String?) : CourseIdResponse

    @GET("courses")
    suspend fun getCourse(): CourseResponse
}