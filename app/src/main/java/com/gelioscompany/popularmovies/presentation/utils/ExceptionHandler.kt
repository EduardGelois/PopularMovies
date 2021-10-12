package com.gelioscompany.popularmovies.presentation.utils

import java.net.UnknownHostException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {
    companion object {

        fun handleException(throwable: Throwable): String {
            return if (throwable is UnknownHostException) {
                // context.getString(R.string.no_internet_connection)
                "askd"
            } else {
                if (throwable != null) throwable.localizedMessage else "Unknown error"
            }
        }
    }
}