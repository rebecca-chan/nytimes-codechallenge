package com.example.repos.data

interface GithubRepoRepository {
    suspend fun getTopThreeRepos(org: String): Results
}