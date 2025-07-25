package com.example.movienight20.ui.search

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MovieSearchScreen(
    viewModel: MovieSearchViewModel,
    onClickMoviePhoto: (Int) -> Unit
) {
    val results = viewModel.resultsViewState.collectAsState()
    val search = viewModel.searchViewState.collectAsState()
    SearchScreen()
}

@Composable
fun SearchScreen(){
    UserInput()
}

@Composable
fun UserInput() {
    val text = remember { mutableStateOf("") }
    TextField(
        value = text.value,
        onValueChange = { newText -> text.value = newText },
        label = { Text("Search") }
    )
}