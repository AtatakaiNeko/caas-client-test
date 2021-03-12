package com.inspirationdriven.caasclient.ui.tags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspirationdriven.caasclient.data.api.ApiService
import com.inspirationdriven.caasclient.utils.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsFragmentViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _tags: MutableLiveData<Resource<List<String>?>> = MutableLiveData()
    val tags: LiveData<Resource<List<String>?>> = _tags

    fun getTags() {
        viewModelScope.launch {
            try {
                _tags.postValue(Resource.Loading)
                apiService.getTags().also { response ->
                    if (response.isSuccessful) {
                        _tags.postValue(Resource.Success(response.body()))
                    } else {
                        _tags.postValue(Resource.Error(Exception(response.code().toString())))
                    }
                }
            } catch (e: Exception) {
                _tags.postValue(Resource.Error(e))
            }
        }
    }
}