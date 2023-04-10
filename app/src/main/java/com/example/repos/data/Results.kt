package com.example.repos.data

import java.lang.Exception

/**
 * Results wrapper class around network response from github search
 */
sealed class Results {
    data class Success(val data: List<GithubRepo>) : Results()
    data class Error(val error: Exception) : Results()
}
