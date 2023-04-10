package com.example.repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.repos.data.Repository
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
    Column(modifier) {
       SearchBar()
       RepoSearchResults(viewModel = viewModel)
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
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
        }
    )
}

@Composable
fun RepoSearchResults(
    modifier: Modifier = Modifier, viewModel: SearchViewModel
) {

    val repos = viewModel.repositories.observeAsState(emptyList())
    LazyColumn() {
        items(repos.value) {
            Column {
                Text(it.fullName)
                Text(it.description)
                Text(it.stars.toString())
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ReposTheme {
//        SearchResultsScreen(viewModel = )
//    }
//}