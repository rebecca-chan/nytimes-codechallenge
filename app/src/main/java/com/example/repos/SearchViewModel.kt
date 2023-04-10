package com.example.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repos.data.GithubRepo
import com.example.repos.data.GithubSearchRepository
import com.example.repos.data.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val repository: GithubSearchRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _githubRepos = MutableStateFlow<List<GithubRepo>>(emptyList())
    val githubRepos: StateFlow<List<GithubRepo>> = _githubRepos

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(2000)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .onEach { query ->

                   val result = repository.getTopThreeRepos(query)
                    when(result) {
                        is Results.Success -> _githubRepos.value = result.data
                        else -> {}
                    }
                }
                .collect()
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

}