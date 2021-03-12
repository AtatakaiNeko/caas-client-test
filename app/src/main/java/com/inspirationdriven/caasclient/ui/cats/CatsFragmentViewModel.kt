package com.inspirationdriven.caasclient.ui.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspirationdriven.caasclient.data.api.ApiService
import com.inspirationdriven.caasclient.data.model.Cat
import com.inspirationdriven.caasclient.utils.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsFragmentViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _cats: MutableLiveData<Resource<List<Cat>?>> = MutableLiveData()
    val cats: LiveData<Resource<List<Cat>?>> = _cats

    fun getCats(tag: String) {
        viewModelScope.launch {
            try {
                apiService.getCats(tag).also { response ->
                    if (response.isSuccessful) {
                        _cats.postValue(Resource.Success(response.body()))
                    } else {
                        _cats.postValue(Resource.Error(Exception(response.code().toString())))
                    }
                }
            } catch (e: Exception) {
                _cats.postValue(Resource.Error(e))
            }
        }
    }
}