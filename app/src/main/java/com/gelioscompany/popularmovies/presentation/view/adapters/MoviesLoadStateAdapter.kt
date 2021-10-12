package com.gelioscompany.popularmovies.presentation.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gelioscompany.popularmovies.R
import com.gelioscompany.popularmovies.databinding.ItemErrorBinding

class MoviesLoadStateAdapter(
    private val context: Context,
) : LoadStateAdapter<MoviesLoadStateAdapter.MoviesLoadStateViewHolder>() {

    class MoviesLoadStateViewHolder(val binding: ItemErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onBindViewHolder(holder: MoviesLoadStateViewHolder, loadState: LoadState) {
        val binding = holder.binding

        when (loadState) {
            is LoadState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
            }
            is LoadState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = context.resources.getString(R.string.connect_error)
            }
            is LoadState.NotLoading -> {
                binding.progressBar.visibility = View.GONE
                binding.errorText.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MoviesLoadStateViewHolder {
        return MoviesLoadStateViewHolder(
            ItemErrorBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
}