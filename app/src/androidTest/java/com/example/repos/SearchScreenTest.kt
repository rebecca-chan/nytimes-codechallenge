package com.example.repos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.repos.data.GithubRepoFixtures
import com.example.repos.ui.search.RepoItem
import com.example.repos.ui.search.SearchBar
import com.example.repos.ui.search.SearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchBar() {
        var searchQuery = ""
        composeTestRule.setContent{
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )
        }

        val newInput = "nytimes"
        composeTestRule.onNodeWithTag("searchBarInput")
            .performTextInput(newInput)
        assertEquals(newInput, searchQuery)
    }
}



