package com.example.repos.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: GithubRepoApi by lazy {
        Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GithubRepoApi::class.java)
    }
}