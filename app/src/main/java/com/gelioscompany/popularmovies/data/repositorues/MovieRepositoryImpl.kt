package com.gelioscompany.popularmovies.data.repositorues

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.gelioscompany.popularmovies.data.network.MoviesSource
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import com.gelioscompany.popularmovies.domain.repository.MoviesRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesSource: MoviesSource
) : MoviesRepository<Pager<Int, MoviesModel>> {

    // configure page settings
    private val pagingConfig = PagingConfig(
        20,
        20,
        false,
        20,
        500
    )

    override fun getMovies(): Pager<Int, MoviesModel> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { moviesSource }
        )
    }
}