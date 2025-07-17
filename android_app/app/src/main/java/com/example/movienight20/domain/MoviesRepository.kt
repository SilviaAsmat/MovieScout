package com.example.movienight20.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.movienight20.data.room.MovieInfoBasicEntity
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType


interface MoviesRepository {
    suspend fun getPopularMovies(): List<MoviesCollectionInfo>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieCredits(movieId: Int): MovieCredits
    suspend fun getPeopleMovieCredits(personId: Int): PeopleMovieCredits
    suspend fun getPeopleDetails(personId: Int): PeopleDetails
    suspend fun getNowPlaying(): List<MoviesCollectionInfo>
    suspend fun storeDataInCache(movie: MovieDetails)
    suspend fun getUpcomingMovies(): List<MoviesCollectionInfo>
    suspend fun getTopRated(): List<MoviesCollectionInfo>
    fun getRecentlyViewed(): Flow<List<MovieInfoBasic>>
    fun moviesPagination(collectionType: MovieCollectionType): Flow<PagingData<MovieInfoBasicEntity>>
}