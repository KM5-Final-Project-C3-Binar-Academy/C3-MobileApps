package com.c3.mobileapps.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.c3.mobileapps.data.remote.model.APIService
import com.c3.mobileapps.util.Resource
import kotlinx.coroutines.Dispatchers

class CourseViewModel(private val apiService: APIService): ViewModel() {

    fun getAllCategory() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success( apiService.getCourses()))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }


}