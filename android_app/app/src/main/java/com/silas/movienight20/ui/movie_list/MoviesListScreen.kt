package com.silas.movienight20.ui.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.silas.movienight20.ui.components.TopAppBar
import com.silas.movienight20.ui.theme.MovieNight20Theme
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    onClickMovieListItem: (Int) -> Unit,
    viewModel: MoviesListScreenViewModel,
) {
    val lazyPagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()
    val topAppBarViewState by viewModel.topAppBarViewState.collectAsState()
    Scaffold(
        topBar = {TopAppBar(topAppBarViewState.getStringName())}
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
            items(
                lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val item = lazyPagingItems[index]
                when(item) {
                    is MovieListItemViewState.Data -> MovieListItem(viewState = item, onClickMovieListItem = onClickMovieListItem)
                    is MovieListItemViewState.Loading -> {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.LightGray)
                                .height(310.dp)
                                .fillMaxWidth()
                        )
                    } // TODO: Move to pagination
                }

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieNight20Theme {
    }
}



