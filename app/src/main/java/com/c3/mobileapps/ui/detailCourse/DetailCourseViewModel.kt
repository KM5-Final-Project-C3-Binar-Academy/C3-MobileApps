package com.c3.mobileapps.ui.detailCourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.course.CourseIdResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseMaterialResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class DetailCourseViewModel(private val repository: DataRepository, private val sharedPref: SharedPref): ViewModel()  {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin



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



    private var _listKelas: MutableLiveData<Resource<CourseMaterialResponse>> = MutableLiveData()
    val listKelas: LiveData<Resource<CourseMaterialResponse>> get() = _listKelas


    fun getCourseByUser(id: String?) = viewModelScope.launch {
        getCourseMe(id)
    }


    private suspend fun getCourseMe(id: String? = null) {
        try {
            val responses = repository.remote.getCourseUserMaterial(token, id)
            _listKelas.value = Resource.success(responses)
        } catch (exception: Exception) {
            _listKelas.value = Resource.error(null, exception.message ?: "Error Occurred!")
        }
    }

    //enrolled free
    //enrolled premium



}