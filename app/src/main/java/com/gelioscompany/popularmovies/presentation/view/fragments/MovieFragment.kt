package com.gelioscompany.popularmovies.presentation.view.fragments

import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.data.network.util.Constants
import com.gelioscompany.popularmovies.databinding.FragmentMovieBinding
import com.gelioscompany.popularmovies.databinding.ViewToolbarBinding
import com.gelioscompany.popularmovies.domain.models.MoviesModel
import com.gelioscompany.popularmovies.presentation.utils.viewBinding
import com.gelioscompany.popularmovies.presentation.view.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class MovieFragment : BaseFragment(R.layout.fragment_movie) {

    private val viewBinding by viewBinding(FragmentMovieBinding::bind)
    private lateinit var toolbarBinding: ViewToolbarBinding

    private val args: MovieFragmentArgs by navArgs()

    override fun initView() {
        toolbarBinding = viewBinding.toolbarGroup
        toolbarBinding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindingView() {
        setupDetailInformation(args.movie)
    }

    private fun setupDetailInformation(movie: MoviesModel) {
        Glide.with(viewBinding.posterImage)
            .load(Constants.URL_IMAGE + movie.posterPath)
            .into(viewBinding.posterImage)

        var title = resources.getString(R.string.original_title)
        title += ":\n${movie.originalTitle}"
        viewBinding.originalTitleText.text = title

        toolbarBinding.fragmentTitleText.text = movie.title
        viewBinding.ratingBar.rating = movie.voteAverage.toFloat()

        val voteCount = "${resources.getString(R.string.vote_count)}: ${movie.voteCount}"
        viewBinding.voteCountText.text = voteCount

        viewBinding.overviewText.text = movie.overview

        if (movie.releaseDate != null) {
            val date = convertTime(movie.releaseDate)
            val releaseDat = "${resources.getString(R.string.release_date)}: ${date}"
            viewBinding.releaseDateText.text = releaseDat
        } else {
            viewBinding.releaseDateText.visibility = View.GONE
        }


    }

    private fun convertTime(time: String): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = fmt.parse(time)

        val fmtOut = SimpleDateFormat("dd.MM.yyyy")
        return fmtOut.format(date)
    }
}