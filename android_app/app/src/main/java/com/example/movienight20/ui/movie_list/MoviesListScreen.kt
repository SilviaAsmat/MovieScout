package com.example.movienight20.ui.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.movienight20.ui.theme.MovieNight20Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    onClickMovieListItem: (Int) -> Unit,
    viewModel: MoviesListScreenViewModel,
    navController: NavController
) {
    val viewStates by viewModel.viewState.collectAsState()
    val lazyPagingItems = viewModel.popMovies.collectAsLazyPagingItems()
    val topAppBarViewState by viewModel.topAppBarViewState.collectAsState()
    //TODO: Implement Android Paging 3
    Scaffold(
        topBar = {TopAppBar(topAppBarViewState.getStringName())}
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(Color.Black)
                .padding(innerPadding),
        ) {
            items(
                lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val item = lazyPagingItems[index]
                MovieListItem(viewState = item!!, onClickMovieListItem = onClickMovieListItem)
            }
        }
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .background(Color.Black)
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(0.dp),
//            horizontalArrangement = Arrangement.spacedBy(0.dp)
//        )
//        {
//            items(items = viewStates) {
//                MovieListItem(viewState = it, onClickMovieListItem = onClickMovieListItem)
//            }
//        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopAppBar(collectionType: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#134e03".toColorInt())
        ),
        title = {
            Text( text = "$collectionType Movies",
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



