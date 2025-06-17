package com.example.movienight20.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column (modifier = Modifier.height(370.dp).fillMaxWidth().background(Color.White)) {
        MoviePoster(id = viewState.id, url = viewState.url, onClickMovieListItem)
        Title(title = viewState.title)
        Row {
            YearLabel(year = viewState.year, modifier = Modifier.weight(1f))
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
        modifier = Modifier.fillMaxWidth().height(300.dp).clickable {
            onClickMovieListItem(id)
        },
        contentScale = ContentScale.FillWidth
    )
}
@Composable
private fun YearLabel(year: String, modifier: Modifier = Modifier) {
    Text(text = year, modifier = modifier
        .padding(5.dp), fontSize = 14.sp)
}
@Composable
private fun RatingLabel(rating: String, modifier: Modifier = Modifier) {
    Text(text = rating, modifier = modifier
        .padding(5.dp), fontSize = 14.sp)

}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        fontSize = 17.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold
    )

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
