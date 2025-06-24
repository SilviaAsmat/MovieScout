package com.example.movienight20.data

import android.util.Log
import com.example.movienight20.domain.Cast
import com.example.movienight20.domain.Genre
import com.example.movienight20.domain.MovieCredits
import com.example.movienight20.domain.MovieDetails
import com.example.movienight20.domain.PopularMoviesInfo
import com.example.movienight20.domain.MoviesRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkService: MovieDatabaseNetworkService
): MoviesRepository {
    override suspend fun getMovies(): List<PopularMoviesInfo> {
        val networkResponse = networkService.getPopularMovies()
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            Log.v(TAG, it.toString())
            // Get info from networkResponse and store into MovieInfo
            PopularMoviesInfo(
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
        return MovieDetails(
            id = results!!.id!!,
            title = results!!.title!!,
            backdropPath = results!!.backdropPath!!,
            overview = results!!.overview!!,
            runtime = results!!.runtime!!,
            status = results!!.status!!,
            genres = results!!.genres!!.map {
                Genre(id = it.id!!, title = it.title!!)
            },
            releaseDate = results!!.releaseDate!!,
            voteAvg = results!!.voteAvg!!,
            voteCount = results!!.voteCount!!,
            tagline = results!!.tagline!!
        )
    }

    private companion object {
        private const val TAG = "MovieRepositoryImpl"
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCredits {
        val networkResponse = networkService.getMovieCredits(movieId = movieId)
        val results = networkResponse.body()
        return MovieCredits(
            movieId = results!!.movieId!!,
            cast = results!!.cast!!.map {
                Cast(
                    castId = it.castId,
                    name = it.name,
                    picturePath = it.picturePath,
                    character = it.character
                )
            }
        )
    }
}