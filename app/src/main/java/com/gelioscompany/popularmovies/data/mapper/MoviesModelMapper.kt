package com.gelioscompany.popularmovies.data.mapper

import com.gelioscompany.popularmovies.data.models.Result
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import javax.inject.Inject

class MoviesModelMapper @Inject constructor() {
    fun transform(list: List<Result>): List<MoviesModel> {
        return list.map { item ->
            MoviesModel(
                adult = item.adult,
                backdropPath = item.backdropPath,
                genreIds = item.genreIds,
                id = item.id,
                originalLanguage = item.originalLanguage,
                originalTitle = item.originalTitle,
                overview = item.overview,
                popularity = item.popularity,
                posterPath = item.posterPath,
                releaseDate = item.releaseDate,
                title = item.title,
                video = item.video,
                voteAverage = item.voteAverage,
                voteCount = item.voteCount
            )
        }
    }
}