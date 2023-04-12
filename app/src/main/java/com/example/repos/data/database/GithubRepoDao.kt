package com.example.repos.data.database

import androidx.room.*
import com.example.repos.data.GithubRepo

@Dao
interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GithubRepo>)

    @Query("SELECT * FROM github_repo WHERE name = :org ORDER BY stars DESC LIMIT 3")
    suspend fun getTop3ReposByOrg(org:String): List<GithubRepo>

}