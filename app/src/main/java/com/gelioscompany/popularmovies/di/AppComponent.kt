package com.gelioscompany.popularmovies.di

import dagger.Component
import com.gelioscompany.popularmovies.presentation.view.fragments.HomeFragment
import com.gelioscompany.popularmovies.presentation.viewmodel.ViewModelFactory
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelsModule::class])
interface AppComponent {

    var factory: ViewModelFactory

    fun inject(homeFragment: HomeFragment)
}
