package cz.utb.fai.ap7mt.moviesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.MovieItemBinding
import cz.utb.fai.ap7mt.moviesapp.network.Movie

class MovieViewHolder(
        private val binding: MovieItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie){
        binding.titleAndYearItem.text = movie.title + " (" + movie.year + ")"
        binding.directorItem.text = movie.director
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            val binding = MovieItemBinding.bind(view)
            return MovieViewHolder(binding)
        }
    }
}