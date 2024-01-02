package com.c3.mobileapps.data.remote.model.response.course

import com.c3.mobileapps.data.remote.model.response.course.Course
import com.google.gson.annotations.SerializedName

data class CourseMaterialResponse(
    @SerializedName("data")
    val data: Course
)