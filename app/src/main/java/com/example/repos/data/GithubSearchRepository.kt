package com.example.repos.data

import javax.inject.Inject

class GithubSearchRepository @Inject constructor(
    private val api: GithubSearchApi
) {

    suspend fun getTopThreeRepos(org: String): Results {
        val userPrefix = "user:"
        val query = userPrefix + org
       return try {
            val results = api.getTopThreeRepos(query)
            return Results.Success(results.items)
        } catch (e: Exception) {
            Results.Error(e)
        }
    }
}