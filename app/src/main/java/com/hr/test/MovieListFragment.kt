package com.hr.test

import androidx.recyclerview.widget.LinearLayoutManager
import com.hr.models.Movie

class MovieListFragment : BaseMovieRecyclerViewFragment() {
    override fun createLayoutManager() = LinearLayoutManager(context)
    override fun createMovieDetailsAction(movie: Movie) = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie)
}