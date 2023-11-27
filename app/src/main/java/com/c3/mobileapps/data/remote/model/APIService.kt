package com.c3.mobileapps.data.remote.model

import com.c3.mobileapps.data.listCourses.CoursesResponse
import com.c3.mobileapps.data.remote.model.response.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("courses")
    suspend fun getCourses(): CoursesResponse

    @GET("course-categories")
    suspend fun getCategory(): CategoryResponse

}