package com.example.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.repos.data.GithubRepoFixtures
import com.example.repos.fakes.FakeGithubRepoRepository
import com.example.repos.ui.search.SearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchViewModelTest {

    private val repository = FakeGithubRepoRepository()
    private lateinit var viewModel: SearchViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is empty when viewModel is initialized`() = runTest {
        val uiState = SearchViewModel.UiState(
            searchQuery = "",
            githubRepos = emptyList(),
            loading = false,
            error = ""
        )
        assertEquals(viewModel.uiState.value, uiState)
    }

    @Test
    fun `uiState is updated when search query changes`() = runTest {
        val uiState = SearchViewModel.UiState(
            searchQuery = "nytimes", githubRepos = emptyList(),
            loading = false, error = ""
        )
        viewModel.onSearchQueryChanged("nytimes")
        assertEquals(viewModel.uiState.value, uiState)
    }

    @Test
    fun `uiState is updated with github repos when search query changes`() = runTest {
        val uiState = SearchViewModel.UiState(
            searchQuery = "nytimes", githubRepos = GithubRepoFixtures.repos,
            loading = false, error = ""
        )
        repository.setSuccessfulResults()
        viewModel.onSearchQueryChanged("nytimes")
        viewModel.searchRepoByOrg(viewModel.uiState.value)

        assertEquals(viewModel.uiState.value, uiState)
    }

}