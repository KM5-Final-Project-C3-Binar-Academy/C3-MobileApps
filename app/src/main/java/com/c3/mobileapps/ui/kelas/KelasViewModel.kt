package com.c3.mobileapps.ui.kelas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
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

class KelasViewModel(private val repository: DataRepository, private val sharedPref: SharedPref, private val categorydao: Categorydao): ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin



    private var _typeFilter: MutableLiveData<String?> = MutableLiveData(null)

    val typeFilter: LiveData<String?> = _typeFilter

    fun setType(type: String?){
        _typeFilter.value = type
    }


    /*Category
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
    }*/

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

    fun getLocalItem() {
        val localData = categorydao.getAllItem()
        Log.e("cekdb", localData.toString())
        val category = arrayListOf<CategoryLocal>()
        localData.forEach {
            category.add(it.toDomain())
        }
        _listCategoryLocal.postValue(category)
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