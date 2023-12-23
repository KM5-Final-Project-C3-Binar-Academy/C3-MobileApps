package com.c3.mobileapps.ui.kelas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.local.database.categoryDB.TbCategory
import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KelasViewModel(private val repository: DataRepository, private val sharedPref: SharedPref): ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin

    // Category
    val readCategory: LiveData<List<TbCategory>> = repository.local.readCategory().asLiveData()
    private fun insertCategory(tbCategory: TbCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertCategory(tbCategory)
    }

    private var _listCategory: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    val listCategory: LiveData<Resource<CategoryResponse>> get() = _listCategory

    private var _typeFilter: MutableLiveData<String?> = MutableLiveData(null)

    val typeFilter: LiveData<String?> = _typeFilter

    fun setType(type: String?){
        _typeFilter.value = type
    }
    fun getListCategory() = viewModelScope.launch {
        getCategory()
    }

    private suspend fun getCategory() {
        try {
            val responses = repository.remote.getCategory()
            _listCategory.value = Resource.success(responses)

            val listCategory = _listCategory.value!!.data
            if (listCategory != null) {
                offlineCategory(listCategory)
            }

        } catch (exception: Exception) {
            _listCategory.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }

    private fun offlineCategory(categoryResponse: CategoryResponse) {
        val category = TbCategory(categoryResponse)
        insertCategory(category)
    }


    private var _listKelas: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    val listKelas: LiveData<Resource<CourseResponse>> get() = _listKelas
    fun getListKelas(type: String?) = viewModelScope.launch {
        getKelas(type)
    }

    private suspend fun getKelas(type: String? = null) {
        try {

            val responses = repository.remote.getCourseUser(token, type)
            _listKelas.value = Resource.success(responses)

        } catch (exception: Exception) {
            _listKelas.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }
}