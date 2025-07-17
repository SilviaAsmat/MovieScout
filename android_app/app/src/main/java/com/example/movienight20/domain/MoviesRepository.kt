package com.example.movienight20.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.movienight20.data.room.MovieInfoBasicEntity as MovieInfoBasicData


interface MoviesRepository {
    suspend fun getMovies(): List<MoviesCollectionInfo>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieCredits(movieId: Int): MovieCredits
    suspend fun getPeopleMovieCredits(personId: Int): PeopleMovieCredits
    suspend fun getPeopleDetails(personId: Int): PeopleDetails
    suspend fun getNowPlaying(): List<MoviesCollectionInfo>
    suspend fun storeDataInCache(movie: MovieDetails)
    suspend fun getUpcomingMovies(): List<MoviesCollectionInfo>
    suspend fun getTopRated(): List<MoviesCollectionInfo>
    fun getRecentlyViewed(): Flow<List<MovieInfoBasic>>
    fun popularMoviesPagination(): Flow<PagingData<MovieInfoBasicData>>
//    fun popularMoviesSinglePage(): Flow<List<MoviesCollectionInfo>>
}