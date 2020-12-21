package cz.utb.fai.ap7mt.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoviesApi.retrofitService.getMovieByTitle("Tenet").enqueue(
            object: Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    findViewById<TextView>(R.id.response).text = t.message
                }

                override fun onResponse(call: Call<Movie>,
                                        response: Response<Movie>
                ) {
                    findViewById<TextView>(R.id.response).text = response.body()?.director
                }

            }
        )
    }
}