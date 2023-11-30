package com.c3.mobileapps.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.database.courseDB.TbCourse
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: DataRepository): ViewModel() {

    private var _mode: MutableLiveData<Any> = MutableLiveData("All")
    val mode: LiveData<Any> = _mode

    val readMenu: LiveData<List<TbCourse>> = repository.local.readCourse().asLiveData()
    private fun insertMenu(tbCourse: TbCourse) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertCourse(tbCourse)
    }

    private var _listCourse: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    val listCourse: LiveData<Resource<CourseResponse>> get() = _listCourse
    fun getListCourse() = viewModelScope.launch {
        getAllCourse()
    }

    private suspend fun getAllCourse() {
        try {
            val responses = repository.remote.getCourse()
            _listCourse.value = Resource.success(responses)

            val listCourse = _listCourse.value!!.data
            if (listCourse != null) {
                offlineMenu(listCourse)
            }

        } catch (exception: Exception) {
            _listCourse.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }

    private fun offlineMenu(courseResponse: CourseResponse) {
        val course = TbCourse(courseResponse)
        insertMenu(course)
    }

    fun setMode(mode:Any){
        _mode.value = mode
    }



}