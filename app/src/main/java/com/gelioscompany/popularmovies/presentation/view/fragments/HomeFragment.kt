package com.gelioscompany.popularmovies.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.databinding.FragmentHomeBinding
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import com.gelioscompany.popularmovies.presentation.app.MoviesApp.Companion.appComponent
import com.gelioscompany.popularmovies.presentation.utils.ConsumableValue
import com.gelioscompany.popularmovies.presentation.utils.viewBinding
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesComparator
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesListAdapter
import com.gelioscompany.popularmovies.presentation.view.adapters.MoviesLoadStateAdapter
import com.gelioscompany.popularmovies.presentation.view.adapters.OnItemClickListener
import com.gelioscompany.popularmovies.presentation.view.base.BaseFragment
import com.gelioscompany.popularmovies.presentation.viewmodel.MainViewModel
import io.reactivex.disposables.Disposable

class HomeFragment : BaseFragment(R.layout.fragment_home), OnItemClickListener {

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

        listAdapter = MoviesListAdapter(MoviesComparator(), this)
    }

    override fun initView() {
        val toolbarView = viewBinding.toolbarGroup
        toolbarView.backImage.visibility = View.INVISIBLE
        toolbarView.fragmentTitleText.text = resources.getString(R.string.popular_movies)

        viewBinding.moviesRecycle.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.moviesRecycle.adapter =
            listAdapter.withLoadStateFooter(
                MoviesLoadStateAdapter(requireContext())
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // subscribe to state
        disposable = viewModel.state.subscribe { homeScreenState ->
            listAdapter.submitData(lifecycle, homeScreenState.pagedList)

            connectionWasLost?.consume { listAdapter.retry() }

            if (!homeScreenState.hasInternetConnection)
                connectionWasLost = ConsumableValue(Unit)
        }
    }

    override fun onDestroyView() {
        // unsubscribe
        disposable.dispose()

        super.onDestroyView()
    }

    override fun onClickItem(movie: MoviesModel) {
        findNavController().navigate(
            HomeFragmentDirections.openDetailScreen(
                movie
            )
        )
    }
}