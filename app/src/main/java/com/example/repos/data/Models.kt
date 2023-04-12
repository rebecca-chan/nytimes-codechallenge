package com.example.repos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int,
    @SerializedName("items") val items: List<GithubRepo>
)

@Entity(tableName = "github_repo")
data class GithubRepo(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String? = "",
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("html_url") val url: String
)
