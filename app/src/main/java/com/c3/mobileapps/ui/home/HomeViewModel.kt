package com.c3.mobileapps.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.database.categoryDB.TbCategory
import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    fun getAllCourse() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.remote.getCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    val readCategory: LiveData<List<TbCategory>> = repository.local.readCategory().asLiveData()
    private fun insertCategory(tbCategory: TbCategory) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertCategory(tbCategory)
    }

    private var _listCategory: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    val listCategory: LiveData<Resource<CategoryResponse>> get() = _listCategory
    fun getListCategory() = viewModelScope.launch {
        getAllCategory()
    }

    private suspend fun getAllCategory() {
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
}