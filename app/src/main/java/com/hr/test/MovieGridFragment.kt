package com.hr.test

import androidx.recyclerview.widget.GridLayoutManager
import com.hr.models.Movie

class MovieGridFragment : BaseMovieRecyclerViewFragment() {
    override fun createLayoutManager() = GridLayoutManager(context, 2)
    override fun createMovieDetailsAction(movie: Movie) = MovieGridFragmentDirections.actionMovieGridFragmentToMovieDetailsFragment(movie)
}