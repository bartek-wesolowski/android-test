package com.hr.test

import androidx.recyclerview.widget.GridLayoutManager

class MovieGridFragment : BaseMovieRecyclerViewFragment() {
    override fun createLayoutManager() = GridLayoutManager(context, 2)
}