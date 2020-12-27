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
import cz.utb.fai.ap7mt.moviesapp.storage.MovieDatabase
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

    private val viewModel: SearchViewModel by lazy {
        val activity = requireActivity()
        ViewModelProvider(this, SearchViewModel.Factory(activity.application))
                .get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

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

        viewModel.searchMovie(::showErrorMessage, ::showMovieDetail)
    }

    private fun showMovieDetail(
            title: String,
            director: String,
            year: String,
            runtime: String,
            released: String,
            plot: String
    ) {
        binding.searchProgressBar.visibility = GONE
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
                title, director, year, runtime, released, plot
        )
        findNavController().navigate(action)
    }

    private fun showErrorMessage(errorMessage: String){
        binding.searchProgressBar.visibility = GONE
        Toast.makeText(context, errorMessage, LENGTH_LONG).show()
    }
}