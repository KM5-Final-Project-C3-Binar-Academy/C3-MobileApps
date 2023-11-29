package com.c3.mobileapps.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.c3.mobileapps.data.repository.CourseRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.Dispatchers

class CourseViewModel(private val courseRep: CourseRepository): ViewModel() {

    fun getAllCourse() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success( courseRep.getCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }


}