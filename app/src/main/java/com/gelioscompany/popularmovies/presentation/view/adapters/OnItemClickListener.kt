package com.gelioscompany.popularmovies.presentation.view.adapters

import com.gelioscompany.popularmovies.domain.models.MoviesModel

interface OnItemClickListener {

    fun onClickItem(movie: MoviesModel)
}