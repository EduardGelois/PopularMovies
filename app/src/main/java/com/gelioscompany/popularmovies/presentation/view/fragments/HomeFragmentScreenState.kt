package com.gelioscompany.popularmovies.presentation.view.fragments

import androidx.paging.PagingData
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import com.gelioscompany.popularmovies.presentation.utils.ConsumableValue

data class HomeFragmentScreenState(
    val pagedList: PagingData<MoviesModel>,
    val hasInternetConnection: Boolean
)