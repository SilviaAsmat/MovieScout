package com.example.movienight20.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET

interface MovieDatabaseNetworkService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<PopularMoviesNetworkResponse>
}

