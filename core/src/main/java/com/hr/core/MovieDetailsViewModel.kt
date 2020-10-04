package com.hr.core

import com.hr.core.MovieDetailsViewModel.Input.*
import com.hr.core.MovieDetailsViewModel.State
import com.hr.core.MovieDetailsViewModel.State.Loaded
import com.hr.core.MovieDetailsViewModel.State.Uninitialized
import com.hr.models.Movie
import com.koduok.mvi.Mvi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailsViewModel(
    private val moviesLikeManager: MoviesLikeManager
) : Mvi<MovieDetailsViewModel.Input, State>(Uninitialized), MoviesLikeManager.LikesChangeListener {

    init {
        moviesLikeManager.addLikesChangeListener(this)
    }

    fun show(movie: Movie) {
        input(Show(movie))
    }

    fun toggleLike() {
        input(ToggleLike)
    }

    override fun onLikesChange() {
        input(RefreshLikeStatus)
    }

    override fun close() {
        moviesLikeManager.removeLikesChangeListener(this)
        super.close()
    }

    override fun handleInput(input: Input) = when (input) {
        is Show -> doShow(input.movie)
        ToggleLike -> doToggleLike()
        RefreshLikeStatus -> doRefreshLikeStatus()
    }

    private fun doShow(movie: Movie): Flow<State> = flow {
        emit(Loaded(MovieItem(movie, moviesLikeManager.isLiked(movie))))
    }

    private fun doToggleLike(): Flow<State> = flow {
        state.movieItem?.let { item ->
            if (item.isLiked) {
                moviesLikeManager.unlike(item.movie)
            } else {
                moviesLikeManager.like(item.movie)
            }
        }
    }

    private fun doRefreshLikeStatus(): Flow<State> = flow {
        state.movieItem?.movie?.let { movie ->
            emit(Loaded(MovieItem(movie, moviesLikeManager.isLiked(movie))))
        }
    }

    sealed class Input {
        internal data class Show(val movie: Movie) : Input()
        internal object ToggleLike : Input()
        internal object RefreshLikeStatus : Input()
    }

    sealed class State {
        abstract val movieItem: MovieItem?

        object Uninitialized : State() {
            override val movieItem: MovieItem? = null
        }

        data class Loaded(override val movieItem: MovieItem) : State()
    }
}