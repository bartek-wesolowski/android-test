package com.hr.core

import com.hr.models.Movie

class MoviesLikeManager {

    private val likedMovies = mutableSetOf<Movie>()

    fun isLiked(movie: Movie) = movie in likedMovies

    fun like(movie: Movie) {
        likedMovies.add(movie)
    }

    fun unlike(movie: Movie) {
        likedMovies.remove(movie)
    }
}