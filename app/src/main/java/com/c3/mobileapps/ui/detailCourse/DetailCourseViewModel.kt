package com.c3.mobileapps.ui.detailCourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.remote.model.response.CourseId.CourseIdResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class DetailCourseViewModel(private val repository: DataRepository): ViewModel()  {

    private var _courseById: MutableLiveData<Resource<CourseIdResponse>> = MutableLiveData()
    val courseById: LiveData<Resource<CourseIdResponse>> get() = _courseById


    fun getCourseById(id: String?) = viewModelScope.launch {
        getCourse(id)
    }


    private suspend fun getCourse(id: String? = null) {
        try {

            val responses = repository.remote.getCourseId(id = id)
            _courseById.value = Resource.success(responses)

        } catch (exception: Exception) {
            _courseById.value = Resource.error(null, exception.message ?: "Error Occurred!")
        }
    }

}