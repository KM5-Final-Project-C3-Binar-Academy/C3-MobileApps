package com.c3.mobileapps.data.remote.model.response.course

import com.google.gson.annotations.SerializedName

data class CourseIdResponse(
    @SerializedName("data")
    val data: Course
)