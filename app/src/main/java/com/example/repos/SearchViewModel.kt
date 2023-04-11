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

    val uiState = MutableStateFlow(UiState())

    init {
        /**
         * Flow set up to collect typed search queries and search for repos by query
         */
        viewModelScope.launch {
            uiState
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.searchQuery.isNotBlank() }
                .onEach {
                    searchRepoByOrg(it)
                }
                .collect()
        }
    }

    private suspend fun searchRepoByOrg(it: UiState) {
        uiState.update { it.copy(loading = true) }
        val result = repository.getTopThreeRepos(it.searchQuery)
        when (result) {
            is Results.Success -> uiState.update {
                it.copy(
                    loading = false,
                    githubRepos = result.data
                )
            }
            is Results.Error -> uiState.update {
                it.copy(
                    loading = false,
                    error = result.error.message.toString()
                )
            }
        }
        uiState.update { it.copy(loading = false) }
    }

    fun onSearchQueryChanged(query: String) {
        uiState.update {
            it.copy(searchQuery = query, error = "")
        }
    }

    data class UiState(
        val searchQuery: String = "",
        val githubRepos: List<GithubRepo> = emptyList(),
        val loading: Boolean = false,
        val error: String = ""
    )

}