package cz.utb.fai.ap7mt.moviesapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.storage.MovieRepository
import cz.utb.fai.ap7mt.moviesapp.storage.getDatabase
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val _emptyList = MutableLiveData<Boolean>()
    var emptyList: MutableLiveData<Boolean>
            get() = _emptyList
            set(value) { _emptyList.value = value.value }

    // TODO: Implement the ViewModel
    private val moviesRepository: MovieRepository by lazy {
        MovieRepository(getDatabase(application.applicationContext))
    }

    fun getMovies(adapter: MovieAdapter) {
        viewModelScope.launch {
            val movies = moviesRepository.getMovies()
            emptyList.value = movies.isEmpty()

            if (requireNotNull(emptyList.value) == false){
                movies.reversed()
                adapter.submitList(movies)
            }
        }
    }
}