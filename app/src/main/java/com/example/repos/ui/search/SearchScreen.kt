package com.example.repos.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.repos.data.GithubRepo
import com.example.repos.R

@Composable
fun SearchResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onRepoClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier) {
        SearchBar(searchQuery = uiState.searchQuery, onSearchQueryChanged = {
            viewModel.onSearchQueryChanged(it)
        })
        if (uiState.githubRepos.isNotEmpty()) {
            RepoSearchResults(repos = uiState.githubRepos, onRepoClick = onRepoClick)
        } else if (uiState.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize(),
                    strokeWidth = 4.dp
                )
            }
        } else if (uiState.error.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.search_error),
                style = MaterialTheme.typography.h5,
                modifier = modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(com.example.repos.R.string.search_placeholder))
        },
        singleLine = true,
    )
}

@Composable
fun RepoSearchResults(
    modifier: Modifier = Modifier, repos: List<GithubRepo>,
    onRepoClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(repos) {
            RepoItem(repo = it, onRepoClick = onRepoClick)
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun RepoItem(modifier: Modifier = Modifier, repo: GithubRepo, onRepoClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = repo.fullName, style = MaterialTheme.typography.h5,
            color = Color.Blue,
            modifier = Modifier.clickable(onClick = { onRepoClick(repo.url) })
        )
        repo.description?.let { Text(modifier = Modifier.padding(top = 8.dp), text = it) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Star, contentDescription = "star", tint = Color.Yellow)
            Text(repo.stars.toString())
        }
    }
}