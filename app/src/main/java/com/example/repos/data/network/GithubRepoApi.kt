package com.example.repos.data.network

import com.example.repos.data.RepoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoApi {

    @GET("/search/repositories?sort=stars&per_page=3")
    suspend fun getTopThreeRepos(
        @Query("q") query: String,
    ): Response<RepoSearchResponse>
}
