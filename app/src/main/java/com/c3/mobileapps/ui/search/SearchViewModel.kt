package com.c3.mobileapps.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: DataRepository) : ViewModel() {

    private var _resultSearch: MutableLiveData<Resource<CourseResponse>> = MutableLiveData()
    val resultSearch: LiveData<Resource<CourseResponse>> get() = _resultSearch

    fun getSearchCourse(
        search: String?
    ) = viewModelScope.launch {
        getSearch(search)
    }

    private suspend fun getSearch(
                                  search: String? = null) {
        try {
            val responses = repository.remote.getCourse(
                type = null,
                category = null,
                filter = null,
                difficulty = null,
                search = search
            )
            _resultSearch.value = Resource.success(responses)

        } catch (exception: Exception) {
            _resultSearch.value = Resource.error(null, exception.message ?: "Error Occurred!")
        }
    }
}