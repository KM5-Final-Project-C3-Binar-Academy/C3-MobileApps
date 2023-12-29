package com.c3.mobileapps.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.database.categoryDB2.CategoryEntity
import com.c3.mobileapps.data.local.database.categoryDB2.Categorydao
import com.c3.mobileapps.data.local.database.categoryDB2.toDomain
import com.c3.mobileapps.data.local.model.CategoryLocal
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.remote.model.response.course.toDomain
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: DataRepository, private val categorydao: Categorydao): ViewModel() {

    private var _listCourse: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    val listCourse: LiveData<Resource<CourseResponse>> get() = _listCourse
    fun getListCourse(cat: String) = viewModelScope.launch {
        getCourse(cat)
    }

    private suspend fun getCourse(cat: String? = "All") {
        try {

            val responses = repository.remote.getCourse(type = null, category =  if (cat != "All") cat else null, filter = null, difficulty =  null, search = null)
            _listCourse.value = Resource.success(responses)

        } catch (exception: Exception) {
            _listCourse.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }


    private var _listCategory2: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val listCategory2: LiveData<Resource<List<Category>>> get() = _listCategory2
    private var _listCategoryLocal = MutableLiveData<List<CategoryLocal>>()
    val lisCategoryLocal : LiveData<List<CategoryLocal>>get() = _listCategoryLocal
    fun getListCategory2() = viewModelScope.launch {
        getCategori()
    }

    private suspend fun getCategori() {
        try {
            val responses = repository.remote.getCategory().data
            _listCategory2.value = Resource.success(responses)

            val listCategory2 = _listCategory2.value!!.data
            if (!listCategory2.isNullOrEmpty()) {
                responses.let {
                    val category = arrayListOf<CategoryLocal>()
                    categorydao.deleteAll()
                    it.forEach {ct->
                        category.add(ct.toDomain())
                        Log.e("NEWLOCAL", category.toString())
                        categorydao.insert(CategoryEntity(id = ct.id, name = ct.name, image = ct.image))
                    }
                    _listCategoryLocal.postValue(category)
                }
            }else{
                getLocalItem()
            }

        } catch (exception: Exception) {
            _listCategory2.value = Resource.error( null,  exception.message ?: "Error Occurred!")
            getLocalItem()
        }

    }

    private fun getLocalItem() {
        val localData = categorydao.getAllItem()
        Log.e("cekdb", localData.toString())
        val category = arrayListOf<CategoryLocal>()
        localData.forEach {
            category.add(it.toDomain())
        }
        _listCategoryLocal.postValue(category)
    }
}