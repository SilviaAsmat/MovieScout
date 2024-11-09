package com.example.movienight20.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDatabaseNetworkService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<PopularMoviesNetworkResponse>
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailsNetworkResponse>
}

