package com.example.repos.fakes

import com.example.repos.data.RepoSearchResponse
import com.example.repos.data.network.GithubRepoApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class FakeGithubRepoApi : GithubRepoApi {

    private lateinit var repoSearchResponse: Response<RepoSearchResponse>

    override suspend fun getTopThreeRepos(query: String): Response<RepoSearchResponse> {
        repoSearchResponse.let {
            return repoSearchResponse
        }
    }

    fun setSuccessfulResponse() {
        repoSearchResponse =
            Response.success(RepoSearchResponse(total = 8, items = GithubRepoFixtures.repos))
    }

    fun setErrorResponse() {
        repoSearchResponse = Response.error(
            400, ResponseBody.create(
                "text/plain".toMediaTypeOrNull(), "error message"
            )
        )
    }
}
