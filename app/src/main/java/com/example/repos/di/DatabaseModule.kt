package com.example.repos.di

import android.content.Context
import androidx.room.Room
import com.example.repos.data.GithubRepoDao
import com.example.repos.data.GithubRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGithubRepoDao(db: GithubRepoDatabase): GithubRepoDao {
        return db.GithubRepoDao()
    }

    @Provides
    @Singleton
    fun provideGithubRepoDatabase(@ApplicationContext context: Context): GithubRepoDatabase {
        return Room.databaseBuilder(context, GithubRepoDatabase::class.java, "github-repo-db")
            .build()
    }
}