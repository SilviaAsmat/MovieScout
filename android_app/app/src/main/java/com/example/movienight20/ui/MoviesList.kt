package com.example.movienight20.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movienight20.ui.theme.MovieNight20Theme
import com.example.movienight20.ui.theme.PopularMoviesViewModel

@Composable
fun MoviesList(
    viewModel: PopularMoviesViewModel
) {
    val viewStates by viewModel.viewState.collectAsState()
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(viewStates) {
//            MovieListItem(viewState = it)
//        }
//    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.background(Color.Blue),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items = viewStates) {
            MovieListItem(viewState = it)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieNight20Theme {
    }
}



