package com.gelioscompany.popularmovies.presentation.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.databinding.FragmentHomeBinding
import com.gelioscompany.popularmovies.presentation.app.MoviesApp.Companion.appComponent
import com.gelioscompany.popularmovies.presentation.utils.ConsumableValue
import com.gelioscompany.popularmovies.presentation.utils.viewBinding
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesComparator
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesListAdapter
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesLoadStateAdapter
import com.gelioscompany.popularmovies.presentation.view.base.BaseFragment
import com.gelioscompany.popularmovies.presentation.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: MainViewModel by viewModels {
        requireContext().appComponent.factory
    }

    private lateinit var listAdapter: MoviesListAdapter
    private lateinit var disposable: Disposable

    private var connectionWasLost: ConsumableValue<Unit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)

        listAdapter = MoviesListAdapter(MoviesComparator())
    }

    override fun initView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = spanSizeLookup

        val toolbarView = viewBinding.toolbarGroup
        toolbarView.backImage.visibility = View.INVISIBLE
        toolbarView.fragmentTitleText.text = resources.getString(R.string.popular_movies)

        viewBinding.moviesRecycle.layoutManager = layoutManager
        viewBinding.moviesRecycle.adapter =
            listAdapter.withLoadStateFooter(
                MoviesLoadStateAdapter()
            )
        listAdapter.setOnItemClickListener { movie ->
            findNavController().navigate(
                HomeFragmentDirections.openDetailScreen(
                    movie
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // subscribe to state
        disposable = viewModel.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { homeScreenState ->
                listAdapter.submitData(lifecycle, homeScreenState.pagedList)

                connectionWasLost?.consume { listAdapter.retry() }

                if (!homeScreenState.hasInternetConnection)
                    connectionWasLost = ConsumableValue(Unit)

                if (!homeScreenState.hasInternetConnection && listAdapter.itemCount == 0) {
                    showDialog()
                }
            }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.connect_error))
        builder.setMessage(resources.getString(R.string.message_dialog_connect_error))
        builder.setCancelable(true)

        builder.setPositiveButton(
            "OK"
        ) { dialog, id -> dialog.cancel() }
        builder.setIcon(getMyDrawable(R.drawable.ic_smile))

        val alert = builder.create()
        alert.show()
    }

    override fun onDestroyView() {
        // unsubscribe
        disposable.dispose()

        super.onDestroyView()
    }

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == listAdapter.itemCount) {
                2
            } else 1
        }
    }
}