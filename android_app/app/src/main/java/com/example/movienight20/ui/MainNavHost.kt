package com.example.movienight20.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.movienight20.ui.details.movie.MovieDetailsScreen
import com.example.movienight20.ui.details.people.PeopleDetailsScreen
import com.example.movienight20.ui.details.people.PeopleDetailsViewModel
import com.example.movienight20.ui.home.HomeScreenViewModel
import com.example.movienight20.ui.home.HomeScreen
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.example.movienight20.ui.movie_list.MoviesListScreen
import com.example.movienight20.ui.theme.MovieDetailsViewModel
import com.example.movienight20.ui.movie_list.MoviesListScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
data class MoviesList(val collectionType: String)

@Serializable
data class MovieDetails(val id: Int)

@Serializable
data class PeopleDetails(val id: Int)

@Serializable
object MainScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        modifier = modifier
    ) {
        composable<MainScreen> {
            val args = it.toRoute<MainScreen>()
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onClickMovieCollection = {collectionType: MovieCollectionType ->
                    navController.navigate(MoviesList(collectionType.name))},
                onClickMoviePhoto = { id: Int ->
                    navController.navigate(MovieDetails(id))
                }
            )
        }
        composable<MoviesList> {
            val args = it.toRoute<MoviesList>()
            val viewModel: MoviesListScreenViewModel = hiltViewModel()
            MoviesListScreen(
                onClickMovieListItem = { id: Int ->
                    navController.navigate(MovieDetails(id))
                },
                viewModel = viewModel,
                navController = navController
            )
        }

        composable<MovieDetails> { backStackEntry ->
            val movieDetails: MovieDetails = backStackEntry.toRoute()
            val viewModel: MovieDetailsViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.initWithID(movieDetails.id)
            }
            MovieDetailsScreen(
                onClickMovieListItem = {},
                viewModel = viewModel,
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                },
                onClickCastPhoto = { id: Int ->
                    navController.navigate(PeopleDetails(id))
                }

            )
        }
        composable<PeopleDetails> { backStackEntry ->
            val peopleDetails: PeopleDetails = backStackEntry.toRoute()
            val viewModel: PeopleDetailsViewModel = hiltViewModel()
            viewModel.initWithID(id = peopleDetails.id)
            PeopleDetailsScreen(
                onClickCastPhoto = {},
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onClickMoviePhoto = { newMovieId: Int ->
                    navController.navigate(MovieDetails(newMovieId))
                }
            )
        }

    }
}
