package com.c3.mobileapps.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.c3.mobileapps.data.repository.CourseRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val courseRep: CourseRepository): ViewModel() {
    fun getAllCategory() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = courseRep.getCategory()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllCourse() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = courseRep.getCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}