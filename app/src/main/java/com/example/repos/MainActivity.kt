package com.example.repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.repos.data.GithubRepo
import com.example.repos.ui.theme.ReposTheme

class MainActivity : ComponentActivity() {

    val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReposTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SearchResultsScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun SearchResultsScreen(modifier: Modifier = Modifier, viewModel: SearchViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.githubRepos.collectAsState()

    Column(modifier) {
        SearchBar(searchQuery = searchQuery, onSearchQueryChanged = {
            viewModel.onSearchQueryChanged(it)
        })
        RepoSearchResults(repos = searchResults)
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
            Text(stringResource(R.string.search_placeholder))
        },
        singleLine = true,
    )
}

@Composable
fun RepoSearchResults(
    modifier: Modifier = Modifier, repos: List<GithubRepo>
) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(repos) {
            RepoItem(repo = it)
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun RepoItem(modifier: Modifier = Modifier, repo: GithubRepo) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = repo.fullName, style = MaterialTheme.typography.h5,
                color = Color.Blue,
            )
            Text(modifier = Modifier.padding(top = 8.dp), text = repo.description)
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReposTheme {
        val item = GithubRepo(
            name = "NYTimes",
            fullName = "nytimes/coolRepo",
            "a cool repo",
            1000,
            "https://github.com"
        )
//        RepoItem(repo = item)
        SearchBar(searchQuery = "nytimes", onSearchQueryChanged = {})
    }
}