package com.example.movienight20.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.domain.Genre
import com.example.movienight20.ui.theme.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(
    onClickMovieListItem: () -> Unit,
    viewModel:MovieDetailsViewModel,
    navController: NavController
) {
    val viewState by viewModel.viewState.collectAsState()
    val errorToastViewState by viewModel.errorToastViewState.collectAsState()
    MovieDetailsScreen(viewState = viewState, onClickMovieDetailsScreen = onClickMovieListItem)

}

@Composable
private fun MovieDetailsScreen(
    viewState: MovieDetailsScreenViewState,
    onClickMovieDetailsScreen: () -> Unit,
) {
    Column (modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.LightGray)) {
        MoviePoster(url = viewState.backdropPath, onClickMovieDetailsScreen)
        Title(title = viewState.title)
        Tagline(tagline = viewState.tagline)
        Row {
            ReleaseDateLabel(releaseDate = viewState.releaseDate, modifier = Modifier.weight(1f))
            VoteAvgLabel(voteAvg = viewState.voteAvg)
            VoteCountLabel(voteCount = viewState.voteCount)
        }
        Overview(overview = viewState.overview)
        Genres(genres = viewState.genres)
    }
}

@Composable
private fun MoviePoster(
    url: String,
    onClickMovieListItem: () -> Unit,
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(url).build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp, 20.dp, 16.dp, 0.dp)
            .clickable {
            onClickMovieListItem()
        },
        contentScale = ContentScale.FillWidth
    )
}
@Composable
private fun ReleaseDateLabel(releaseDate: String, modifier: Modifier = Modifier) {
    Text(
        text = releaseDate,
        modifier = modifier
            .padding(16.dp, 0.dp, 10.dp, 0.dp),
        fontSize = 16.sp,
        color = Color.Black
    )
}
@Composable
private fun VoteAvgLabel(voteAvg: Number, modifier: Modifier = Modifier) {
    Text(
        text = voteAvg.toString(),
        fontSize = 16.sp,
        color = Color.Black
    )

}
@Composable
private fun VoteCountLabel(voteCount: Int, modifier: Modifier = Modifier) {
    Text(
        text = "($voteCount)",
        fontSize = 16.sp,
        modifier = modifier
            .padding(3.dp, 0.dp, 16.dp, 0.dp),
        color = Color.Black
    )

}
@Composable
private fun Overview(overview: String, modifier: Modifier = Modifier) {
    Text(
        text = overview,
        fontSize = 16.sp,
        modifier = modifier
            .padding(16.dp, 8.dp, 16.dp, 4.dp),
        color = Color.Black
    )

}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 10.dp, 0.dp),
        fontSize = 22.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

}

@Composable
private fun Genres(genres: List<Genre>, modifier: Modifier = Modifier) {
//    Text(
//        text = genres.get(1).title.toString()
//    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    )
    {
        items(items = genres) {
            Text(
                text = it.title.toString(),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(16.dp, 8.dp, 16.dp, 4.dp),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}

@Composable
private fun Tagline(tagline: String, modifier: Modifier = Modifier) {
    Text(
        text = tagline,
        fontSize = 16.sp,
        modifier = modifier
            .padding(16.dp, 8.dp, 16.dp, 4.dp),
        color = Color.Black,
        fontStyle = FontStyle.Italic
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailsScreen() {
    MovieDetailsScreen(
        viewState = MovieDetailsScreenViewState(
            id = 12345,
            title = "SomeMovieThatIsOkay",
            backdropPath = "Poster",
            overview = "blahblahblahblahblahblahblahblahblahblahblahblahblahblahblahb\nlahblahblahblahblahblahblahblahblahblahblahblahblahblahblah",
            runtime = 90,
            status = "released",
            genres = listOf(),
            releaseDate = "11-11-1111",
            voteAvg = 198,
            voteCount = 111,
            tagline = "this epic movie is about to get more epic"
        ),
        onClickMovieDetailsScreen ={})
}
