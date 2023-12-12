package com.c3.mobileapps.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.database.categoryDB.TbCategory
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: DataRepository) : ViewModel() {


    private var _mode: MutableLiveData<String?> = MutableLiveData(null)
    val mode: LiveData<String?> = _mode
    fun setMode(mode: String?) {
        _mode.value = mode
    }

    /**
    private var _category: MutableLiveData<String> = MutableLiveData()
    val category: LiveData<String> = _category
    fun setCategory(category: String) {
        _category.value = category
    }

    private var _difficulty: MutableLiveData<String> = MutableLiveData()
    val difficulty: LiveData<String> = _difficulty
    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }

    private var _filter: MutableLiveData<String> = MutableLiveData()
    val filter: LiveData<String> = _filter
    fun setFilter(filter: String) {
        _filter.value = filter
    }
    **/

    private var _dataFilter: MutableLiveData<MutableMap<String, MutableList<String>>> =
        MutableLiveData(mutableMapOf())
    val dataFilter: LiveData<MutableMap<String, MutableList<String>>> = _dataFilter
    fun setIsFiltered( data: MutableMap<String, MutableList<String>>) {
        _dataFilter.value = data
    }

    fun addDataMapping(key: String, value: String?) {
        val currentData = _dataFilter.value ?: mutableMapOf()

        if (currentData.containsKey(key)) {
            if (value != null) {
                currentData[key] = mutableListOf(value)
            }
        } else {
            currentData[key] = mutableListOf(value.toString())
        }

        _dataFilter.value = currentData
    }



    private var _listCourse: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    val listCourse: LiveData<Resource<CourseResponse>> get() = _listCourse
    fun getListCourse(
        type: String?,
        filter: String?,
        category: String?,
        search: String?,
        difficulty: String?
    ) = viewModelScope.launch {
        getCourse(type, filter, category, search, difficulty)
    }

    private suspend fun getCourse(type: String? = null,
                                  filter: String? = null,
                                  category: String? = null,
                                  search: String? = null,
                                  difficulty: String? = null) {
        try {

            val responses = repository.remote.getCourse(
                type = type,
                category = category,
                filter = filter,
                difficulty = difficulty,
                search = search
            )
            _listCourse.value = Resource.success(responses)

        } catch (exception: Exception) {
            _listCourse.value = Resource.error(null, exception.message ?: "Error Occurred!")
        }
    }

    val readCategory: LiveData<List<TbCategory>> = repository.local.readCategory().asLiveData()


}