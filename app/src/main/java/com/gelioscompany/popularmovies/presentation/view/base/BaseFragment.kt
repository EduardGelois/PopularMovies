package com.gelioscompany.popularmovies.presentation.view.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
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

    fun getMyDrawable(id: Int): Drawable? {
       return ContextCompat.getDrawable(requireContext(), id)
    }
}