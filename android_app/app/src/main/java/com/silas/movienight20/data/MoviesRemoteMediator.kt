package com.silas.movienight20.data

import android.util.Log
import android.webkit.ConsoleMessage.MessageLevel.LOG
import com.silas.movienight20.data.room.MovieScoutDatabase
import com.silas.movienight20.data.room.remote_keys.RemoteKeysEntity

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.silas.movienight20.data.room.movie_collection_types.NowPlayingMoviesEntity
import com.silas.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity
import com.silas.movienight20.data.room.movie_collection_types.PopularMoviesEntity
import com.silas.movienight20.data.room.movie_collection_types.TopRatedMoviesEntity
import com.silas.movienight20.data.room.movie_collection_types.UpcomingMoviesEntity
import com.silas.movienight20.ui.movie_collection_type.MovieCollectionType

import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApiService: MovieDatabaseNetworkService,
    private val moviesDatabase: MovieScoutDatabase,
    private val collectionType: MovieCollectionType
) : RemoteMediator<Int, MovieInfoBasicEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS)
        return if (
            System.currentTimeMillis() - moviesDatabase.getRemoteKeysDao()
                .getCreationTime(collectionType.name) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao()
                .getRemoteKeyByMovieID(movie.remoteId, collectionType.name)

        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(
                movie.remoteId,
                collectionType.name
            )
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(id, collectionType.name)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieInfoBasicEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {

                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = when (collectionType) {
                MovieCollectionType.POPULAR -> moviesApiService.getPopularMovies(page = page)
                MovieCollectionType.NOW_PLAYING -> moviesApiService.getNowPlaying(page = page)
                MovieCollectionType.TOP_RATED -> moviesApiService.getTopRatedMovies(page = page)
                MovieCollectionType.UPCOMING -> moviesApiService.getUpcomingMovies(page = page)
            }

            val movies = apiResponse.body()?.results
            val moviesResult = movies!!.map { data ->
                MovieInfoBasicEntity(
                    remoteId = data.id!!,
                    name = data.title ?: "",
                    posterPath = "http://image.tmdb.org/t/p/w1280${data.posterPath}",
                    backdropPath = "http://image.tmdb.org/t/p/w1280${data.backdropPath}",
                    releaseDate = data.releaseDate!!,
                    rating = data.voteAverage.toString()
                )
            }

            val endOfPaginationReached = moviesResult.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.v("SAA", "REFRESH " + collectionType.name)
                    moviesDatabase.getRemoteKeysDao().clearRemoteKeys(collectionType.name)
                    when (collectionType) {
                        MovieCollectionType.POPULAR -> {
                            moviesDatabase.getPopularMoviesDao().clearMovies()
                        }

                        MovieCollectionType.NOW_PLAYING -> {
                            moviesDatabase.getNowPlayingMoviesDao().clearMovies()
                        }

                        MovieCollectionType.TOP_RATED -> {
                            moviesDatabase.getTopRatedMoviesDao().clearMovies()
                        }

                        MovieCollectionType.UPCOMING -> {
                            moviesDatabase.getUpcomingMoviesDao().clearMovies()
                        }
                    }
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val remoteKeyEntities = movies.map {
                    RemoteKeysEntity(
                        movieID = it.id!!,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                        collectionType = collectionType.name
                    )
                }

                moviesDatabase.getRemoteKeysDao().insertAll(remoteKeyEntities)
                moviesDatabase.movieInfoBasicDao().insertAllPopularMovies(moviesResult)

                when (collectionType) {
                    MovieCollectionType.POPULAR -> moviesDatabase.getPopularMoviesDao()
                        .addMovies(movies.map {
                            PopularMoviesEntity(
                                localId = 0,
                                remoteId = it.id!!
                            )
                        })

                    MovieCollectionType.NOW_PLAYING -> moviesDatabase.getNowPlayingMoviesDao()
                        .addMovies(movies.map {
                            NowPlayingMoviesEntity(
                                localId = 0,
                                remoteId = it.id!!
                            )
                        })

                    MovieCollectionType.TOP_RATED -> moviesDatabase.getTopRatedMoviesDao()
                        .addMovies(movies.map {
                            TopRatedMoviesEntity(
                                localId = 0,
                                remoteId = it.id!!
                            )
                        })

                    MovieCollectionType.UPCOMING -> moviesDatabase.getUpcomingMoviesDao()
                        .addMovies(movies.map {
                            UpcomingMoviesEntity(
                                localId = 0,
                                remoteId = it.id!!
                            )
                        })
                }


            }
            Log.v("SAA", "success")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}