package com.example.movienight20.data

import android.util.Log
import com.example.movienight20.domain.MovieDetails
import com.example.movienight20.domain.MovieInfo
import com.example.movienight20.domain.MoviesRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkService: MovieDatabaseNetworkService
): MoviesRepository {
    override suspend fun getMovies(): List<MovieInfo> {
        val networkResponse = networkService.getPopularMovies()
        // results will get the List<Item> from @results from PopularMoviesNetworkResponse
        // through separation of concerns
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            Log.v(TAG, it.toString())
            // Get info from networkResponse and store into MovieInfo
            MovieInfo(
                id = it.id!!,
                title = it.title!!,
                backdropPath = it.backdropPath!!,
                posterPath = it.posterPath!!,
                releaseDate = it.releaseDate!!,
                rating = it.voteAverage!!.toString()
            )
        }
        return mapped
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val networkResponse = networkService.getMovieDetails(movieId = movieId)

        val results = networkResponse.body()
        // TODO map from JSON to domain

        return MovieDetails()
    }

    private companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}