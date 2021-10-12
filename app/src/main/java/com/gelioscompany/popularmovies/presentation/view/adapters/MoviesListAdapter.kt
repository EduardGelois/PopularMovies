package com.gelioscompany.popularmovies.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.data.network.util.Constants.URL_IMAGE
import com.gelioscompany.popularmovies.databinding.ItemMovieBinding
import com.gelioscompany.popularmovies.domain.models.MoviesModel

class MoviesListAdapter(
    diffCallback: DiffUtil.ItemCallback<MoviesModel>
) : PagingDataAdapter<MoviesModel, MoviesListAdapter.MoviesViewHolder>(diffCallback) {

    private var onItemClickListener: ((MoviesModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (MoviesModel) -> Unit) {
        onItemClickListener = listener
    }

    class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        return MoviesViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder, position: Int
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
                onItemClickListener?.invoke(movie)
            }
        }
    }
}