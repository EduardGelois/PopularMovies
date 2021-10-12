package com.gelioscompany.popularmovies.presentation.view.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gelioscompany.popularmovies.R

open class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected open fun initView() {}

    protected open fun bindingView() {}
    protected open fun initViewModel() {}
    protected open fun loadingObserve() {}
    protected open fun exceptionObserve() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindingView()
        initViewModel()
        loadingObserve()
        exceptionObserve()
    }

    open fun onBackPressed(): Boolean {
        return false
    }


    open fun navController(navHostFragment: Int = R.id.navHostFragment): NavController =
        Navigation.findNavController(
            requireActivity(),
            navHostFragment
        )
}