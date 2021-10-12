package com.gelioscompany.popularmovies.domain.repository

interface MoviesRepository<T> {

    fun getMovies(): T
}