package com.example.repos.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    private val api: GithubRepoApi,
    private val dao: GithubRepoDao
) {

    suspend fun getTopThreeRepos(org: String): Results {
        val userPrefix = "user:"
        val query = userPrefix + org
        return withContext(Dispatchers.IO) {
            try {
                // check db first
                val reposFromDb = dao.getTop3ReposByOrg(org)
                if (reposFromDb.isNotEmpty()) {
                    Results.Success(reposFromDb)
                } else {
                    // if not in db, make network request
                    val results = api.getTopThreeRepos(query)
                    // insert into db
                    dao.insertAll(results.items)
                    Results.Success(results.items)
                }
            } catch (e: IOException) {
                Results.Error(e)
            } catch (e: HttpException) {
                Results.Error(e)
            } catch(e: Exception) {
                Results.Error(e)
            }
        }
    }
}