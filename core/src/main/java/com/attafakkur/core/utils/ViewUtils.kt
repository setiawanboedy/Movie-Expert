package com.attafakkur.core.utils

import android.view.View
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

fun ProgressBar.showPb() {
    visibility = View.VISIBLE
}

fun ProgressBar.hidePb() {
    visibility = View.GONE
}

fun ShimmerFrameLayout.showPb() {
    visibility = View.VISIBLE
}

fun ShimmerFrameLayout.hidePb() {
    visibility = View.GONE
}

fun View.snack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun refresh(swipeRefreshLayout: SwipeRefreshLayout?, listener: (Any) -> Unit) {
    swipeRefreshLayout?.setOnRefreshListener {
        listener.also(listener)

        swipeRefreshLayout.isRefreshing = false
    }
}