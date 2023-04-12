package com.example.repos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repos.data.GithubRepo

@Database(entities = [GithubRepo::class], version = 1)
abstract class GithubRepoDatabase : RoomDatabase() {

    abstract fun GithubRepoDao() : GithubRepoDao
}