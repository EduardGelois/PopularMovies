package com.gelioscompany.popularmovies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import com.gelioscompany.popularmovies.domain.repository.MoviesRepository
import com.gelioscompany.popularmovies.presentation.view.fragments.HomeFragmentScreenState
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    moviesRepository: MoviesRepository<Pager<Int, MoviesModel>>
) : ViewModel() {

    // combined two flowable in one
    val state: Flowable<HomeFragmentScreenState> = Flowable.combineLatest(
        moviesRepository.getMovies().flowable.cachedIn(viewModelScope),
        ReactiveNetwork.observeInternetConnectivity().toFlowable(BackpressureStrategy.LATEST)
    ) { pagedList, hasInternetConnection ->

        HomeFragmentScreenState(
            pagedList = pagedList,
            hasInternetConnection = hasInternetConnection
        )
    }
}