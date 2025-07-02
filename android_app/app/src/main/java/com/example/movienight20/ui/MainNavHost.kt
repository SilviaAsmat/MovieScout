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
import com.example.movienight20.ui.home.MainScreen
import com.example.movienight20.ui.movie_list.MoviesListScreen
import com.example.movienight20.ui.theme.MovieDetailsViewModel
import com.example.movienight20.ui.theme.PopularMoviesViewModel
import kotlinx.serialization.Serializable

@Serializable
object PopularMoviesList

@Serializable
data class MovieDetails(val id: Int)

@Serializable
data class PeopleDetails(val id: Int)

@Serializable
object MainScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    popularMoviesViewModel: PopularMoviesViewModel,
    detailsViewModel: MovieDetailsViewModel,
    peopleDetailsViewModel: PeopleDetailsViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        //startDestination = PopularMoviesList,
        modifier = modifier
    ) {
        composable<MainScreen> {
            MainScreen(
                viewModel = homeScreenViewModel,
                onClickMoviePhoto = { id: Int ->
                    navController.navigate(MovieDetails(id))
                })
        }
        composable<PopularMoviesList> {
            popularMoviesViewModel.initViewModel(MovieCollectionType.POPULAR)
            MoviesListScreen(
                viewModel = popularMoviesViewModel,
                onClickMovieListItem = { id: Int ->
                    navController.navigate(MovieDetails(id))
                },
                navController = navController
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
//
//    fun NavHostController.navigateSingleTopTo(route: String) =
//        this.navigate(route) {
//            // Pop up to the start destination of the graph to
//            // avoid building up a large stack of destinations
//            // on the back stack as users select items
//            popUpTo(
//                this@navigateSingleTopTo.graph.findStartDestination().id
//            ) {
//                saveState = true
//            }
//            // Avoid multiple copies of the same destination when
//            // reselecting the same item
//            launchSingleTop = true
//            // Restore state when reselecting a previously selected item
//            restoreState = true
//        }
}
