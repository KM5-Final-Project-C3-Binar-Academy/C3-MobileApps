package com.c3.mobileapps.data.remote.model.response.course


import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("data")
    var `data`: List<Course?>?
)