package com.gelioscompany.popularmovies.di

import androidx.paging.Pager
import com.gelioscompany.popularmovies.data.repositorues.MovieRepositoryImpl
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import dagger.Binds
import dagger.Module

import com.gelioscompany.popularmovies.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindMoviesRepository(impl: MovieRepositoryImpl): MoviesRepository<Pager<Int, MoviesModel>>
}