package com.example.movienight20.data

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
            // Get info from networkResponse and store into MovieInfo
            MovieInfo(
                id = it.id!!,
                title = it.title!!,
                backdropPath = it.backdropPath!!,
                year = it.releaseDate!!,
                rating = it.voteAverage!!.toString()
            )
        }
        // Is returning mapped needed if MovieInfo has the info?
        return mapped
    }
}