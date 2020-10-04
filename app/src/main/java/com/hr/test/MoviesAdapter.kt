package com.hr.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hr.core.MovieItem
import com.hr.models.Movie

class MoviesAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onToggleLike: (MovieItem) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var items = emptyList<MovieItem>()

    fun setItems(movies: List<MovieItem>) {
        this.items = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movie_item_view, parent, false),
        onMovieClick
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.apply {
            text = item.movie.name.value
            setOnClickListener { onMovieClick(item.movie) }
        }
        holder.likeImage.apply {
            if (item.isLiked) {
                setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            setOnClickListener { onToggleLike(item) }
        }
    }

    data class MovieViewHolder(
        private val itemView: View,
        private val onMovieClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val likeImage: ImageView = itemView.findViewById(R.id.likeImage)
    }
}