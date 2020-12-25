package cz.utb.fai.ap7mt.moviesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

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

    fun searchMovie() : Movie?
    {
        var movie: Movie? = null

        MoviesApi.retrofitService.getMovieByTitle(title.value).enqueue(
            object: Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    //findViewById<TextView>(R.id.response).text = t.message
                }

                override fun onResponse(call: Call<Movie>,
                                        response: Response<Movie>
                ) {
                    movie = response.body()
                }

            }
        )

        return movie
    }
}