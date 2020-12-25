package cz.utb.fai.ap7mt.moviesapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.SearchFragmentBinding
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.searchButton.setOnClickListener {
            validateSearch()

            MoviesApi.retrofitService.getMovieByTitle(viewModel.title.value).enqueue(
                    object: Callback<Movie> {
                        override fun onFailure(call: Call<Movie>, t: Throwable) {
                            Log.i("Search error", t.message?:"abc")
                        }

                        override fun onResponse(call: Call<Movie>,
                                                response: Response<Movie>
                        ) {
                            val movie = response.body()
                            if (movie != null){
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

    private fun validateSearch() {
        if (viewModel.title.value == "" || viewModel.title.value == null)
            binding.titleEditLayout.error = "Title is required"
        else
            binding.titleEditLayout.error = null
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