package com.c3.mobileapps.data.database.categoryDB

import androidx.room.TypeConverter
import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterCategory {
    private var gson = Gson()
    @TypeConverter
    fun categoryToString(categoryResponse: CategoryResponse): String {
        return gson.toJson(categoryResponse)
    }

    @TypeConverter
    fun stringToCategory(data: String): CategoryResponse {
        val list = object : TypeToken<CategoryResponse>() {}.type
        return gson.fromJson(data, list)
    }
}