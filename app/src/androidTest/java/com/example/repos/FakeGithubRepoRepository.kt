package com.example.repos

import com.example.repos.data.GithubRepoFixtures
import com.example.repos.data.GithubRepoRepository
import com.example.repos.data.Results
import java.io.IOException

class FakeGithubRepoRepository: GithubRepoRepository {

    private lateinit var githubRepos: Results

    override suspend fun getTopThreeRepos(org: String): Results {
        githubRepos.let {
            return githubRepos
        }
    }

    fun setSuccessfulResults() {
        githubRepos = Results.Success(data = GithubRepoFixtures.repos)
    }

    fun setFailedResults() {
        githubRepos = Results.Error(IOException())
    }
}