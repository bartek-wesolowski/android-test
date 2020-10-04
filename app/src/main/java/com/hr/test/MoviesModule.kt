package com.hr.test

import com.hr.core.MoviesLikeManager
import com.hr.core.MoviesViewModel
import life.shank.ShankModule
import life.shank.new
import life.shank.scoped
import life.shank.single

object MoviesModule : ShankModule {
    private val moviesSource = new { -> DummyMoviesSourceThatShouldBeReplaced() }
    private val moviesLikeManager = single { -> MoviesLikeManager() }
    val moviesViewModel = scoped { -> MoviesViewModel(moviesSource(), moviesLikeManager()) }
}