package com.example.repos.fakes

import com.example.repos.data.GithubRepo
import com.example.repos.data.database.GithubRepoDao

class FakeGithubRepoDao: GithubRepoDao {

    var githubRepos: List<GithubRepo> = emptyList()

    override suspend fun insertAll(list: List<GithubRepo>) {
        githubRepos = list
    }

    override suspend fun getTop3ReposByOrg(org: String): List<GithubRepo> {
        return githubRepos
    }
}