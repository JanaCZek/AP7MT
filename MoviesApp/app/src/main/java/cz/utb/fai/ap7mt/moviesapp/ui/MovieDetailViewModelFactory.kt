package cz.utb.fai.ap7mt.moviesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailViewModelFactory(
        private val title: String,
        private val year: String,
        private val director: String,
        private val runtime: String,
        private val released: String,
        private val plot: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java))
            return MovieDetailViewModel(title, year, director, runtime, released, plot) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}