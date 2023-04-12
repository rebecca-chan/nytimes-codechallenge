package com.example.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.repos.data.GithubRepoRepositoryImpl
import com.example.repos.data.Results
import com.example.repos.fakes.FakeGithubRepoApi
import com.example.repos.fakes.FakeGithubRepoDao
import com.example.repos.data.GithubRepoFixtures
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
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
class GithubRepoRepositoryTest {

    private val api = FakeGithubRepoApi()
    private val dao = FakeGithubRepoDao()
    private val githubRepoRepository = GithubRepoRepositoryImpl(api = api, dao = dao)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `returns successful result of repos on successful response from network`() =
        runTest {
            api.setSuccessfulResponse()
            val results = githubRepoRepository.getTopThreeRepos("nytimes")

            assertEquals(results, Results.Success(GithubRepoFixtures.repos))
        }

    @Test
    fun `returns error on unsuccessful response from network`() =
        runTest {
            api.setErrorResponse()

            when (val results = githubRepoRepository.getTopThreeRepos("nytimes")) {
                is Results.Success -> {
                    fail("Expected error response, but got success response")
                }
                is Results.Error -> {
                    val actualErrorMessage = results.error.message
                    val expectedErrorMessage = "Network request failed"
                    assertEquals(expectedErrorMessage, actualErrorMessage)
                }
            }
        }

    @Test
    fun `returns successful result of repos from db if results in db`() {
        runTest {
            dao.insertAll(GithubRepoFixtures.repos2)
            val results = githubRepoRepository.getTopThreeRepos("nytimes")

            assertEquals(results, Results.Success(GithubRepoFixtures.repos2))

        }
    }

}