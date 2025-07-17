package com.example.movienight20.data

import com.example.movienight20.data.room.MovieScoutDatabase
import com.example.movienight20.data.room.RemoteKeysEntity

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movienight20.data.room.MovieInfoBasicEntity
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType

import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator (
    private val moviesApiService: MovieDatabaseNetworkService,
    private val moviesDatabase: MovieScoutDatabase,
    private val collectionType: MovieCollectionType
): RemoteMediator<Int, MovieInfoBasicEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - moviesDatabase.getRemoteKeysDao().getCreationTime() < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.remoteId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.remoteId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieInfoBasicEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(id)
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
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
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
                    name = data.title.toString(),
                    posterPath = "http://image.tmdb.org/t/p/w1280${data.posterPath}",
                    backdropPath = "http://image.tmdb.org/t/p/w1280${data.backdropPath}",
                    localId = 0,
                )

            }

            val endOfPaginationReached = moviesResult.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.getRemoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val remoteKeyEntities = movies.map {
                    RemoteKeysEntity(movieID = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                moviesDatabase.getRemoteKeysDao().insertAll(remoteKeyEntities)
                moviesDatabase.movieInfoBasicDao().insertAllPopularMovies(moviesResult)// movies?.onEachIndexed { _, movie -> movie.page = page }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}