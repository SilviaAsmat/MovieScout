package com.example.movienight20.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movienight20.ui.theme.PopularMoviesViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: PopularMoviesViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MovieList.route,
        modifier = modifier
    ) {
        composable(route = MovieList.route) {
            MoviesList(
                onClickMovieListItem = {

                },
                viewModel = viewModel,
            )
        }

    }

    fun NavHostController.navigateSingleTopTo(route: String) =
        this.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(
                this@navigateSingleTopTo.graph.findStartDestination().id
            ) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
}
