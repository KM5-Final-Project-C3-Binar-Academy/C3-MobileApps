package com.c3.mobileapps.data.remote.model.response.course


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    var data: List<Category>
)