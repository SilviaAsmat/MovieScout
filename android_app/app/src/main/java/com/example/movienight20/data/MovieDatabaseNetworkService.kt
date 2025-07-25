package com.example.movienight20.data

import com.example.movienight20.data.network_response.MovieCreditsNetworkResponse
import com.example.movienight20.data.network_response.MovieDetailsNetworkResponse
import com.example.movienight20.data.network_response.MovieVideosNetworkResponse
import com.example.movienight20.data.network_response.PeopleDetailsNetworkResponse
import com.example.movienight20.data.network_response.PeopleMovieCreditsNetworkResponse
import com.example.movienight20.data.network_response.MoviesCollectionsNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDatabaseNetworkService {
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailsNetworkResponse>
    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): Response<MovieCreditsNetworkResponse>
    @GET("/3/person/{person_id}/movie_credits")
    suspend fun getPeopleMovieCredits(@Path("person_id") personId: Int): Response<PeopleMovieCreditsNetworkResponse>
    @GET("/3/person/{person_id}")
    suspend fun getPeopleDetails(@Path("person_id") personId: Int): Response<PeopleDetailsNetworkResponse>
    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int): Response<MovieVideosNetworkResponse>
    @GET("/3/search/movie")
    suspend fun getMovieSearch(@Query("query") query: String): Response<MoviesCollectionsNetworkResponse>


    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviesCollectionsNetworkResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int
    ): Response<MoviesCollectionsNetworkResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Response<MoviesCollectionsNetworkResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Response<MoviesCollectionsNetworkResponse>
}

