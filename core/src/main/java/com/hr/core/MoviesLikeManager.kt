package com.hr.core

import com.hr.models.Movie

class MoviesLikeManager {

    interface LikesChangeListener {
        fun onLikesChange()
    }

    private val likedMovies = mutableSetOf<Movie>()
    private val likesChangeListeners = mutableListOf<LikesChangeListener>()

    fun isLiked(movie: Movie) = movie in likedMovies

    fun like(movie: Movie) {
        likedMovies.add(movie)
        likesChangeListeners.forEach { it.onLikesChange() }
    }

    fun unlike(movie: Movie) {
        likedMovies.remove(movie)
        likesChangeListeners.forEach { it.onLikesChange() }
    }

    fun addLikesChangeListener(listener: LikesChangeListener) {
        likesChangeListeners.add(listener)
    }

    fun removeLikesChangeListener(listener: LikesChangeListener) {
        likesChangeListeners.remove(listener)
    }
}