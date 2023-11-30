package com.c3.mobileapps.ui.detailCourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailCourseViewModel:ViewModel() {

    private var _mode: MutableLiveData<Any> = MutableLiveData("All")
    val mode: LiveData<Any> = _mode


    fun setMode(mode:Any){
        _mode.value = mode
    }

}