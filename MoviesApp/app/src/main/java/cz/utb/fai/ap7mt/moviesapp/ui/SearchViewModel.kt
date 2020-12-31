package cz.utb.fai.ap7mt.moviesapp.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import cz.utb.fai.ap7mt.moviesapp.storage.MovieRepository
import cz.utb.fai.ap7mt.moviesapp.storage.getDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository: MovieRepository by lazy {
        MovieRepository(getDatabase(application.applicationContext))
    }

    private val _title = MutableLiveData<String>()
    var title: MutableLiveData<String>
        get() = _title
        set(value) { _title.value = value.value }

    private val _year = MutableLiveData<String?>()
    var year: MutableLiveData<String?>
        get() = _year
        set(value) { _year.value = value.value }

    init {
        _title.value = ""
        _year.value = ""
    }

    fun searchMovie(errorCallback: (errorMessage: String) -> Unit, navigationCallback: (title: String,
                                         director: String,
                                         year: String,
                                         runtime: String,
                                         released: String,
                                         plot: String,
                                         errorMessage: String?) -> Unit){
        viewModelScope.launch {
            var movie: Movie?
            val titleVal = requireNotNull(title.value)

            movie = if (year.value == "" || year.value == null){
                moviesRepository.getMovieByTitle(titleVal)
            } else {
                val yearVal = requireNotNull(year.value)
                moviesRepository.getMovieByTitleAndYear(titleVal, yearVal)
            }

            if (movie == null)
                try {
                    apiCall(errorCallback, navigationCallback)
                }
                catch (e: Exception) {
                    navigationCallback(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            e.message
                    )
                }
            else
                navigationCallback(
                    movie.title?:"",
                    movie.director?:"",
                    movie.year?:"",
                    movie.runtime?:"",
                    movie.released?:"",
                    movie.plot?:"",
                        null
                )
        }
    }

    private fun apiCall(errorCallback: (errorMessage: String) -> Unit, navigationCallback: (title: String,
                                                            director: String,
                                                            year: String,
                                                            runtime: String,
                                                            released: String,
                                                            plot: String,
                                                            errorMessage: String?) -> Unit) {
        if (year.value == "" || year.value == null)
            MoviesApi.retrofitService.getMovieByTitle(title.value).enqueue(
                object: Callback<Movie> {
                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        errorCallback(t.message?:"Error in API Call")
                    }

                    override fun onResponse(call: Call<Movie>,
                                            response: Response<Movie>
                    ) {
                        val movie = response.body()
                        if (movie != null && movie.response == "True"){
                            viewModelScope.launch {
                                moviesRepository.insert(movie)
                            }
                            navigationCallback(
                                movie.title?:"",
                                movie.director?:"",
                                movie.year?:"",
                                movie.runtime?:"",
                                movie.released?:"",
                                movie.plot?:"",
                                    null
                            )
                        }
                        else
                            navigationCallback(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "Could not find movie. Please try again"
                            )
                    }
                }
            )
        else
            MoviesApi.retrofitService.getMovieByTitleAndYear(title.value, year.value).enqueue(
                object: Callback<Movie> {
                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        errorCallback(t.message?:"Error in API Call")
                    }

                    override fun onResponse(call: Call<Movie>,
                                            response: Response<Movie>
                    ) {
                        val movie = response.body()
                        if (movie != null && movie.response == "True"){
                            viewModelScope.launch {
                                moviesRepository.insert(movie)
                            }
                            navigationCallback(
                                movie.title?:"",
                                movie.director?:"",
                                movie.year?:"",
                                movie.runtime?:"",
                                movie.released?:"",
                                movie.plot?:"",
                                    null
                            )
                        }
                        else
                            navigationCallback(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "Could not find movie. Please try again"
                            )
                    }
                }
            )
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}