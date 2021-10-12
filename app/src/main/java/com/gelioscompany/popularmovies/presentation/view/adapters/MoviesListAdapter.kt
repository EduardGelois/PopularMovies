package com.gelioscompany.popularmovies.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.data.network.util.Constants.URL_IMAGE
import com.gelioscompany.popularmovies.databinding.ItemMovieBinding
import com.gelioscompany.popularmovies.domain.models.MoviesModel

class MoviesListAdapter(
    diffCallback: DiffUtil.ItemCallback<MoviesModel>,
    private val itemClickListener: OnItemClickListener? = null
) : PagingDataAdapter<MoviesModel, MoviesListAdapter.MoviesViewHolder>(diffCallback) {

    class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        return MoviesViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int
    ) {
        val item: MoviesModel? = getItem(position)
        item?.let { movie ->
            val binding = holder.binding

            Glide.with(binding.posterImage)
                .load(URL_IMAGE + item.posterPath)
                .placeholder(R.drawable.place_holder)
                .into(binding.posterImage)

            binding.titleText.text = movie.title
            binding.ratingBar.rating = movie.voteAverage.toFloat()

            binding.container.setOnClickListener {
                binding.motion.tag = "click"
                binding.motion.transitionToEnd()
            }

            binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    if (binding.motion.tag == null)
                        binding.animView.visibility = View.GONE
                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    if (binding.motion.tag != null) {
                        itemClickListener?.onClickItem(movie)
                        binding.motion.tag = null
                        binding.motion.transitionToStart()
                    }
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float
                ) {
                }
            })
        }
    }
}