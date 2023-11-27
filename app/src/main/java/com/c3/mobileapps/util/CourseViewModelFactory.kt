package com.c3.mobileapps.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c3.mobileapps.data.remote.model.APIService
import com.c3.mobileapps.ui.course.CourseViewModel

class CourseViewModelFactory(val apiService: APIService): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            return CourseViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}