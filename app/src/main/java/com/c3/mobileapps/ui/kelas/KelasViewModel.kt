package com.c3.mobileapps.ui.kelas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    // Category
    val readCategory: LiveData<List<TbCategory>> = repository.local.readCategory().asLiveData()
    private fun insertCategory(tbCategory: TbCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertCategory(tbCategory)
    }

    private var _listCategory: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    val listCategory: LiveData<Resource<CategoryResponse>> get() = _listCategory
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
    fun getListKelas() = viewModelScope.launch {
        getKelas()
    }

    private suspend fun getKelas() {
        try {

            val responses = repository.remote.getCourseUser(token)
            _listKelas.value = Resource.success(responses)

        } catch (exception: Exception) {
            _listKelas.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }
}