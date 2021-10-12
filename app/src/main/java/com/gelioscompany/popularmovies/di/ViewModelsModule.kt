package com.gelioscompany.popularmovies.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import com.gelioscompany.popularmovies.presentation.viewmodel.MainViewModel

@Module
interface ViewModelsModule {

    @Binds
    @[IntoMap ClassKey(MainViewModel::class)]
    fun bindMainViewModelFactory(factory: MainViewModel): ViewModel
}