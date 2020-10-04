package com.hr.core

import com.hr.core.MoviesViewModel.Input
import com.hr.core.MoviesViewModel.Input.Refresh
import com.hr.core.MoviesViewModel.Input.ToggleLike
import com.hr.core.MoviesViewModel.State
import com.hr.core.MoviesViewModel.State.Idle
import com.hr.core.MoviesViewModel.State.Loaded
import com.koduok.mvi.Mvi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesViewModel(
    private val moviesSource: MoviesSource,
    private val moviesLikeManager: MoviesLikeManager
) : Mvi<Input, State>(Idle) {

    fun refresh() = input(Refresh)

    fun toggleLike(item: MovieItem) = input(ToggleLike(item))

    override fun handleInput(input: Input): Flow<State> = when (input) {
        Refresh -> doRefresh()
        is ToggleLike -> doToggleLike(input.item)
    }

    private fun doRefresh(): Flow<State> = flow {
        emit(State.Loading(emptyList()))
        val movies = moviesSource.getMovies()
        val items = movies.map { movie -> MovieItem(movie, moviesLikeManager.isLiked(movie)) }
        emit(Loaded(items))
    }

    private fun doToggleLike(item: MovieItem): Flow<State> = flow {
        if (item.isLiked) {
            moviesLikeManager.unlike(item.movie)
        } else {
            moviesLikeManager.like(item.movie)
        }
        emit(Loaded(state.items))
    }

    sealed class Input {
        internal object Refresh : Input()
        internal data class ToggleLike(val item: MovieItem) : Input()
    }

    sealed class State {
        abstract val items: List<MovieItem>

        object Idle : State() {
            override val items: List<MovieItem> get() = emptyList()
        }

        data class Loading(override val items: List<MovieItem>) : State()

        data class Loaded(override val items: List<MovieItem>) : State()
    }
}