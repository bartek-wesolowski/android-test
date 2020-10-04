package com.hr.core

import com.hr.models.Movie

data class MovieItem(
    val movie: Movie,
    val isLiked: Boolean
)