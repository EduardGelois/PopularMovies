package com.gelioscompany.popularmovies.data.network

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import com.gelioscompany.popularmovies.data.mapper.MoviesModelMapper
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesSource @Inject constructor(
    private val movieApi: MovieApi,
    private val moviesModelMapper: MoviesModelMapper
) : RxPagingSource<Int, MoviesModel>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MoviesModel>> {
        val page = params.key ?: 1

        return movieApi.requestPopularMovie(page)
            .map { response ->
                toLoadResult(page, moviesModelMapper.transform(response.results))
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    //method to map Movies to loadResult object
    private fun toLoadResult(
        page: Int,
        list: List<MoviesModel>,
    ): LoadResult<Int, MoviesModel> {
        return LoadResult.Page(list, if (page == 1) null else page - 1, page + 1)
    }
}