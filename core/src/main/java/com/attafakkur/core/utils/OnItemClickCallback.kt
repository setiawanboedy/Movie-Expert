package com.attafakkur.core.utils

import com.attafakkur.core.domain.model.Movie

interface OnItemClickCallback {
    fun onItemClicked(data: Movie)
}