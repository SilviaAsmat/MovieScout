package com.silas.movienight20.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.itemKey
import com.silas.movienight20.ui.movie_list.MovieListItem
import com.silas.movienight20.ui.movie_list.MovieListItemViewState
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieListSinglePage(
    viewState: List<MovieListItemViewState>,
    onClickMovieListItem:(Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    )
    {
        items(
            viewState.size,
        ) { index ->
            val item = viewState[index]
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
                }
            }

        }
    }
}