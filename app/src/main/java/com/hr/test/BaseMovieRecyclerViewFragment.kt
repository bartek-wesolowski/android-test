package com.hr.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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
        val adapter = MoviesAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = createLayoutManager()
        recyclerView.adapter = adapter
        moviesViewModel.collectStatesOn(this) { _, state ->
            adapter.setMovies(state.movies)
        }
    }

    protected abstract fun createLayoutManager(): RecyclerView.LayoutManager
}