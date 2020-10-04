package com.hr.test

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hr.test.MoviesModule.movieDetailsViewModel
import com.koduok.mvi.android.shank.collectStatesOn
import life.shank.android.AutoScoped
import life.shank.android.onReadyFor

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details), AutoScoped {

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.onReadyFor(this) {
            it.show(args.movie)
        }
        movieDetailsViewModel.collectStatesOn(this) { viewModel, state ->
            view.findViewById<TextView>(R.id.titleTextView).text = state.movieItem?.movie?.name?.value
            view.findViewById<ImageView>(R.id.likeImage).apply {
                if (state.movieItem?.isLiked == true) {
                    setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                setOnClickListener {
                    viewModel.toggleLike()
                }
            }
        }
    }
}