package cz.utb.fai.ap7mt.moviesapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.SearchFragmentBinding
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import cz.utb.fai.ap7mt.moviesapp.storage.MovieRepository
import cz.utb.fai.ap7mt.moviesapp.storage.getDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel
    //private val moviesRepository = MovieRepository(getDatabase(context))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.searchButton.setOnClickListener {
            val valid = validateSearch()

            if (valid)
                searchForMovie(viewModel.title.value, viewModel.year.value)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun validateSearch() : Boolean {
        var valid = false

        if (viewModel.title.value == "" || viewModel.title.value == null)
            binding.titleEditLayout.error = "Title is required"
        else {
            binding.titleEditLayout.error = null
            valid = true
        }

        return valid
    }

    private fun searchForMovie(title: String?, year: String?) {

        binding.searchProgressBar.visibility = VISIBLE



        if (year == "" || year == null)
            MoviesApi.retrofitService.getMovieByTitle(title).enqueue(
                    object: Callback<Movie> {
                        override fun onFailure(call: Call<Movie>, t: Throwable) {
                            Toast.makeText(context, t.message?:"Search error", LENGTH_LONG).show()
                            binding.searchProgressBar.visibility = GONE
                        }

                        override fun onResponse(call: Call<Movie>,
                                                response: Response<Movie>
                        ) {
                            binding.searchProgressBar.visibility = GONE
                            val movie = response.body()
                            if (movie != null && movie.response == "True"){
                                showMovieDetail(
                                        movie.title,
                                        movie.director,
                                        movie.year,
                                        movie.runtime,
                                        movie.released,
                                        movie.plot
                                )
                            }
                        }

                    }
            )
        else
            MoviesApi.retrofitService.getMovieByTitleAndYear(title, year).enqueue(
                    object: Callback<Movie> {
                        override fun onFailure(call: Call<Movie>, t: Throwable) {
                            Toast.makeText(context, t.message?:"Search error", LENGTH_LONG).show()
                            binding.searchProgressBar.visibility = GONE
                        }

                        override fun onResponse(call: Call<Movie>,
                                                response: Response<Movie>
                        ) {
                            binding.searchProgressBar.visibility = GONE
                            val movie = response.body()
                            if (movie != null && movie.response == "True"){
                                showMovieDetail(
                                        movie.title,
                                        movie.director,
                                        movie.year,
                                        movie.runtime,
                                        movie.released,
                                        movie.plot
                                )
                            }
                        }
                    }
            )

        binding.searchProgressBar.visibility = GONE
    }

    private fun showMovieDetail(
            title: String,
            director: String,
            year: String,
            runtime: String,
            released: String,
            plot: String
    ) {
        /*val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
                viewModel.title.value?:"abcd",
                "abcd",
                viewModel.year.value?:"abcd",
                "abcd",
                "abcd",
                "abcdedfg"
        )*/
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
                title, director, year, runtime, released, plot
        )
        findNavController().navigate(action)
    }
}