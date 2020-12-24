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
    val title: LiveData<String>
        get() = _title

    private val _year = MutableLiveData<String?>()
    val year: LiveData<String?>
        get() = _year

    private val _director = MutableLiveData<String?>()
    val director: LiveData<String?>
        get() = _director

    private val _runtime = MutableLiveData<String?>()
    val runtime: LiveData<String?>
        get() = _runtime

    init {
        _title.value = "ABCD"
    }

    fun searchMovie()
    {
        MoviesApi.retrofitService.getMovieByTitle(title.value).enqueue(
            object: Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    //findViewById<TextView>(R.id.response).text = t.message
                }

                override fun onResponse(call: Call<Movie>,
                                        response: Response<Movie>
                ) {
                    //findViewById<TextView>(R.id.response).text = response.body()?.director
                }

            }
        )
    }
}