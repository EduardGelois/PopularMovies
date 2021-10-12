package com.gelioscompany.popularmovies.presentation.utils

class ConsumableValue<T>(private val item: T) {

    private var isConsumed = false

    fun consume(block: (T) -> Unit) {
        if (!isConsumed) {
            block.invoke(item)
            isConsumed = true
        }
    }
}