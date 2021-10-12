package com.gelioscompany.popularmovies.data.network

import io.reactivex.Single
import com.gelioscompany.popularmovies.data.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun requestPopularMovie(@Query("page")  page:Int): Single<MoviesResponse>
}