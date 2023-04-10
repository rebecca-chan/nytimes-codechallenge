package com.example.repos.data

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int,
    @SerializedName("items") val items: List<Repository>
)

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("html_url") val url: String
)
