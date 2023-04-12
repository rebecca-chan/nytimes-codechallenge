package com.example.repos.data

import com.example.repos.data.database.GithubRepoDao
import com.example.repos.data.network.GithubRepoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val api: GithubRepoApi,
    private val dao: GithubRepoDao
) : GithubRepoRepository {

    override suspend fun getTopThreeRepos(org: String): Results {
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
                    if (results.isSuccessful && results.body() != null) {
                        dao.insertAll(results.body()!!.items)
                        Results.Success(results.body()!!.items)
                    } else {
                        Results.Error(Exception("Network request failed"))
                    }
                }
            } catch (e: IOException) {
                Results.Error(e)
            } catch (e: HttpException) {
                Results.Error(e)
            } catch (e: Exception) {
                Results.Error(e)
            }
        }
    }
}