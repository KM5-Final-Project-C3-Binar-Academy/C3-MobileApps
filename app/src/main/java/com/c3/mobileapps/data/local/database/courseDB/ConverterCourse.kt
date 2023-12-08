package com.c3.mobileapps.data.local.database.courseDB

import androidx.room.TypeConverter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterCourse {
    private var gson = Gson()
    @TypeConverter
    fun courseToString(courseResponse: CourseResponse): String {
        return gson.toJson(courseResponse)
    }

    @TypeConverter
    fun stringToCourse(data: String): CourseResponse {
        val list = object : TypeToken<CourseResponse>() {}.type
        return gson.fromJson(data, list)
    }
}