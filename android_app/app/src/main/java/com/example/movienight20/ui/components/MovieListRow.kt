package com.example.movienight20.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.ui.MovieCardInfoViewState
import com.example.movienight20.ui.home.HomeScreenViewState

@Composable
fun MovieListRow(
    movieInfo: List<MovieCardInfoViewState>,
    onClickMoviePhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .heightIn(max = 260.dp)
            .padding(start = 12.dp, end = 0.dp, bottom = 0.dp, top = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = movieInfo) {
            Column(modifier = Modifier.height(260.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(it.posterPath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(220.dp)
                        .width(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickMoviePhoto(it.id) },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.title.toString(),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(100.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            }// End of Column
        }
    }
}