package com.example.repos.di

import com.example.repos.data.GithubRepoRepository
import com.example.repos.data.GithubRepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideGithubRepoRepository(impl: GithubRepoRepositoryImpl): GithubRepoRepository
}