package com.example.movienight20.data

import com.example.movienight20.data.room.MovieScoutDatabase
import com.example.movienight20.data.room.RemoteKeys

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movienight20.data.room.MovieInfoBasic

import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator (
    private val moviesApiService: MovieDatabaseNetworkService,
    private val moviesDatabase: MovieScoutDatabase,
): RemoteMediator<Int, MovieInfoBasic>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - moviesDatabase.getRemoteKeysDao().getCreationTime() < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieInfoBasic>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieInfoBasic>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieInfoBasic>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                moviesDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieInfoBasic>
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
            val apiResponse = moviesApiService.getPopularMovies(page = page)

            delay(1000L) //TODO For testing only!

            val movies = apiResponse.body()?.results
            val moviesResult = movies!!.map { data ->
                MovieInfoBasic(
                    id = data.id!!,
                    name = data.title.toString(),
                    posterPath = "http://image.tmdb.org/t/p/w1280${data.posterPath}",
                    backdropPath = "http://image.tmdb.org/t/p/w1280${data.backdropPath}",
                    page = page
                )

            }

            val endOfPaginationReached = moviesResult.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.getRemoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeys(movieID = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                moviesDatabase.getRemoteKeysDao().insertAll(remoteKeys)
                moviesDatabase.movieInfoBasic().insertAllPopularMovies(moviesResult)// movies?.onEachIndexed { _, movie -> movie.page = page }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}