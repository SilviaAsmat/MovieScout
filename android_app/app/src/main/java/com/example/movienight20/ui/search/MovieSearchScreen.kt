package com.example.movienight20.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.movienight20.ui.components.MovieListSinglePage
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movienight20.ui.components.TopAppBar

@Composable
fun MovieSearchScreen(
    viewModel: MovieSearchViewModel,
    onClickMoviePhoto: (Int) -> Unit
) {
    val results by viewModel.resultsViewState.collectAsState()
    val search by viewModel.searchViewState.collectAsState()
    SearchScreen(
        onValueChange = viewModel::onSearchQueryChanged,
        searchResults = results,
        onClickMoviePhoto = onClickMoviePhoto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    onValueChange: (String) -> Unit,
    searchResults: SearchMovieResultsViewState,
    onClickMoviePhoto: (Int) -> Unit
) {
    Column(modifier = Modifier
        .padding()) {
        TopAppBar(title = "Search")
        UserInput(onValueChange = onValueChange)
        when (searchResults) {
            is SearchMovieResultsViewState.Data -> {
                MovieListSinglePage(
                    searchResults.results,
                    onClickMovieListItem = onClickMoviePhoto
                )
            }

            else -> {}
        }
    }
}

@Composable
fun UserInput(onValueChange: (String) -> Unit) {
    val text = remember { mutableStateOf("") }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
        TextField(
            value = text.value,
            onValueChange = { newText ->
                text.value = newText
                onValueChange(newText)
            },
            label = { Text("Search") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )


}