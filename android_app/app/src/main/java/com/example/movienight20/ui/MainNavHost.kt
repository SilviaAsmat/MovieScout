package com.example.movienight20.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
data class MoviesList(val collectionType: MovieCollectionType)

@Serializable
data class MovieDetails(val id: Int)

@Serializable
data class PeopleDetails(val id: Int)

@Serializable
object MainScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    listScreenViewModel: MoviesListScreenViewModel,
    detailsViewModel: MovieDetailsViewModel,
    peopleDetailsViewModel: PeopleDetailsViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        modifier = modifier
    ) {
        composable<MainScreen> {
            HomeScreen(
                viewModel = homeScreenViewModel,
                onClickMoviePhoto = { id: Int ->
                    navController.navigate(MovieDetails(id))
                },
                onClickMovieCollection = {collectionType: MovieCollectionType ->
                    navController.navigate(MoviesList(collectionType))
                }
            )
        }
        composable<MoviesList> { backStackEntry ->
            val moviesList: MoviesList = backStackEntry.toRoute()
            listScreenViewModel.initViewModel(collectionType = moviesList.collectionType)
            MoviesListScreen(
                viewModel = listScreenViewModel,
                onClickMovieListItem = { id: Int ->
                    navController.navigate(MovieDetails(id))
                },
                navController = navController,

            )
        }

        composable<MovieDetails> { backStackEntry ->
            val movieDetails: MovieDetails = backStackEntry.toRoute()
            LaunchedEffect(Unit) {
                detailsViewModel.initWithID(movieDetails.id)
            }
            MovieDetailsScreen(
                onClickMovieListItem = {},
                viewModel = detailsViewModel,
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
            peopleDetailsViewModel.initWithID(id = peopleDetails.id)
            PeopleDetailsScreen(
                onClickCastPhoto = {},
                viewModel = peopleDetailsViewModel,
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
