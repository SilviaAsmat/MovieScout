package com.example.movienight20.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.movienight20.R
import com.example.movienight20.ui.theme.MovieNight20Theme
import com.example.movienight20.ui.theme.PopularMoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    onClickMovieListItem: (Int) -> Unit,
    viewModel: PopularMoviesViewModel,
    navController: NavController
) {
    val viewStates by viewModel.viewState.collectAsState()
    Scaffold(

    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .background(Color.Black)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        )
        {
            items(items = viewStates) {
                MovieListItem(viewState = it, onClickMovieListItem = onClickMovieListItem)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopAppBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#73ad0c".toColorInt())
        ),
        title = {
            Text(
                "Popular Movies",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )},
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieNight20Theme {
    }
}



