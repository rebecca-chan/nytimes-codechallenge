package com.example.repos.data

class GithubRepoFixtures {

    companion object {
        val REPO1 = GithubRepo(
            id = 1,
            name = "nytimes",
            fullName = "nytimes/repo1",
            description = "an interesting repo",
            stars = 100,
            url = "github.com"
        )
        val REPO2 = REPO1.copy(id = 2, fullName = "nytimes/repo2", stars = 1000)
        val REPO3 = REPO1.copy(id = 3, fullName = "nytimes/repo3", stars = 500)
        val REPO4 = REPO1.copy(id = 4, fullName = "nytimes/repo4", stars = 8)
        val repos = listOf(REPO1, REPO2, REPO3)
        val repos2 = listOf(REPO1, REPO3, REPO4)
    }

}