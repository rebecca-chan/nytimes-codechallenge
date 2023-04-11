package com.example.repos.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GithubRepo::class], version = 1)
abstract class GithubRepoDatabase : RoomDatabase() {

    abstract fun GithubRepoDao() : GithubRepoDao
}