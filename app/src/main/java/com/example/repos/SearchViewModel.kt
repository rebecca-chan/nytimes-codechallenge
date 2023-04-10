package com.example.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repos.data.GithubRepo
import com.example.repos.data.RetrofitInstance
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _githubRepos = MutableStateFlow<List<GithubRepo>>(emptyList())
    val githubRepos: StateFlow<List<GithubRepo>> = _githubRepos


    init {
        viewModelScope.launch {
            searchQuery
                .debounce(2000)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .onEach { typedQuery ->
                    val userPrefix = "user:"
                    val query = userPrefix + typedQuery
                    try {
                        val results = RetrofitInstance.api.getTopThreeRepos(query)
                        _githubRepos.value = results.items
                    } catch (e: Exception) {
                        // handle error
                    }
                }
                .collect()
        }
    }



    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

}