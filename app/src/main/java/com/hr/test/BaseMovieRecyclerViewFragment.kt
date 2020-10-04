package com.hr.test

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hr.core.MoviesViewModel.State.Idle
import com.hr.core.MoviesViewModel.State.Loading
import com.hr.models.Movie
import com.hr.test.MoviesModule.moviesViewModel
import com.koduok.mvi.android.shank.collectStatesOn
import life.shank.android.AutoScoped
import life.shank.android.onReadyFor

abstract class BaseMovieRecyclerViewFragment : Fragment(R.layout.fragment_recycler_view), AutoScoped {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel.onReadyFor(this) { it.refresh() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = MoviesAdapter(
            onMovieClick = { movie ->
                findNavController().navigate(createMovieDetailsAction(movie))
            },
            onToggleLike = { movie ->
                moviesViewModel.onReadyFor(this) {
                    it.toggleLike(movie)
                }
            }
        )
        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = createLayoutManager()
            this.adapter = adapter
        }
        moviesViewModel.collectStatesOn(this) { _, state ->
            adapter.submitList(state.items)
            view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (state is Idle || state is Loading) View.VISIBLE else View.GONE
        }
    }

    protected abstract fun createLayoutManager(): RecyclerView.LayoutManager

    protected abstract fun createMovieDetailsAction(movie: Movie): NavDirections
}