package com.hr.core

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.hr.core.MoviesViewModel.State.Loaded
import com.hr.core.MoviesViewModel.State.Loading
import com.hr.models.Movie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutinesExtension::class)
internal class MoviesViewModelTest {

    private val moviesSource = FakeMoviesSource()
    private val viewModel by lazy { MoviesViewModel(moviesSource, MoviesLikeManager()) }

    @Test
    internal fun `starts loading movies automatically`() {
        assertThat(viewModel.state).isInstanceOf(Loading::class)
    }

    @Test
    internal fun `shows loaded movies when they are successfully loaded`() {
        val movies = someListOf<Movie>()
        moviesSource.getMovies.complete(movies)

        assertThat(viewModel.state)
            .isInstanceOf(Loaded::class)
            .transform { state -> state.items.map { it.movie } }
            .isEqualTo(movies)
    }

    @Test
    internal fun `can refresh movies`() {
        val movies = someListOf<Movie>()
        val refreshedMovies = someListOf<Movie>()

        moviesSource.getMovies.complete(movies)
        moviesSource.resetGetMovies()

        viewModel.refresh()
        assertThat(viewModel.state)
            .isInstanceOf(Loading::class)
            .transform { state -> state.items.map { it.movie } }
            .isEqualTo(movies)

        moviesSource.getMovies.complete(refreshedMovies)
        assertThat(viewModel.state)
            .isInstanceOf(Loaded::class)
            .transform { state -> state.items.map { it.movie } }
            .isEqualTo(refreshedMovies)
    }

    @Test
    internal fun `shows error when fetching movies fail, user can recover by refreshing`() {
        TODO("Not yet implemented")
    }
}