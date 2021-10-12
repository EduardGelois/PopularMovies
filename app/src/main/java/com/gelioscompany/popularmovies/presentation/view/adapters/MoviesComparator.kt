package com.gelioscompany.popularmovies.presentation.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gelioscompany.popularmovies.domain.models.MoviesModel

class MoviesComparator : DiffUtil.ItemCallback<MoviesModel>() {
    override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
        return oldItem.id == newItem.id
    }
}