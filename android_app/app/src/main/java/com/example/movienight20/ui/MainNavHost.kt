package com.example.movienight20.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movienight20.ui.theme.MovieDetailsViewModel
import com.example.movienight20.ui.theme.PopularMoviesViewModel
import kotlinx.serialization.Serializable

@Serializable
object PopularMoviesList

@Serializable
object MovieDetails

@Composable
fun MainNavHost(
    navController: NavHostController,
    popularMoviesViewModel: PopularMoviesViewModel,
    detailsViewModel: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PopularMoviesList,
        modifier = modifier
    ) {
        composable<PopularMoviesList> {
            MoviesListScreen(
                viewModel = popularMoviesViewModel,
                onClickMovieListItem = {navController.navigate(route = MovieDetails)}
            )
        }

        composable<MovieDetails>{
            MovieDetailsScreen(
                onClickMovieListItem = {},
                viewModel = detailsViewModel,
                navController = navController
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
