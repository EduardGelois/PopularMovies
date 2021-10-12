package com.gelioscompany.popularmovies.presentation.app

import android.app.Application
import android.content.Context
import com.gelioscompany.popularmovies.BuildConfig
import com.gelioscompany.popularmovies.di.AppComponent
import com.gelioscompany.popularmovies.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber


class MoviesApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        // init dagger component
        appComponent = DaggerAppComponent.create()
        // handle rx errors
        RxJavaPlugins.setErrorHandler { /* no-op */}

        initTimber()
    }

    companion object {
        val Context.appComponent: AppComponent
            get() = when (this) {
                is MoviesApp -> appComponent
                else -> this.applicationContext.appComponent
            }
    }

    private fun initTimber() {
          if (BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
//  todo      else
//            Timber.plant(FabricTree())
    }
}