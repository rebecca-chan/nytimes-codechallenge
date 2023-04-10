package com.example.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repos.data.Repository
import com.example.repos.data.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    init {
        test()
    }

    fun test() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getTopThreeRepos("user:nytimes")
            _repositories.value = response.items
        }
    }

    fun searchRepos(org: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getTopThreeRepos(org)
            _repositories.value = response.items
        }
    }
}