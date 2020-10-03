package com.hr.test

import androidx.recyclerview.widget.LinearLayoutManager

class MovieListFragment : BaseMovieRecyclerViewFragment() {
    override fun createLayoutManager() = LinearLayoutManager(context)
}