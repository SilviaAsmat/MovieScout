package com.example.movienight20.ui.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieListItem(
    viewState: MovieListItemViewState,
    onClickMovieListItem: (Int) -> Unit,
) {
    Column (modifier = Modifier.height(310.dp).fillMaxWidth().background(Color.White)) {
        MoviePoster(id = viewState.id, url = viewState.url, onClickMovieListItem)
        Title(title = viewState.title)
        Row {
            ReleaseDateLabel(year = viewState.year, modifier = Modifier.weight(1f))
            RatingLabel(rating = viewState.rating)
        }
    }
}

@Composable
private fun MoviePoster(
    id: Int,
    url: String,
    onClickMovieListItem: (Int) -> Unit,
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(url).build(),
        contentDescription = null,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
            onClickMovieListItem(id)
        },
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopStart
    )
}
@Composable
private fun ReleaseDateLabel(year: String, modifier: Modifier = Modifier) {
    Text(text = year,
        modifier = modifier
            .padding(8.dp, 0.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}
@Composable
private fun RatingLabel(rating: String, modifier: Modifier = Modifier) {
    Text(text = rating,
        modifier = modifier
            .padding(8.dp, 0.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()) // Enables horizontal scrolling
            .padding(8.dp, 0.dp, 16.dp, 0.dp),
    ) {
            Text(
                text = title,
                //modifier = modifier
                    //.fillMaxWidth(),

                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MovieListItem(
        viewState = MovieListItemViewState(
            id = 2314,
            title = "Some Random Movie Title",
            url = "",
            year = "1999",
            rating = "21"),
        onClickMovieListItem = {})
}
