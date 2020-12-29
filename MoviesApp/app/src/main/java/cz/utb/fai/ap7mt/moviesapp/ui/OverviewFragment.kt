package cz.utb.fai.ap7mt.moviesapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.OverviewFragmentBinding
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var binding: OverviewFragmentBinding
    private lateinit var viewModel: OverviewViewModel
    private val adapter = MovieAdapter(::movieDetailFromList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.moviesList.addItemDecoration(decoration)
        binding.moviesList.adapter = adapter

        binding.searchFragmentButton.setOnClickListener {
            showSearchFragment()
        }

        viewModel.emptyList.observe(viewLifecycleOwner, { emptyList ->
            if (emptyList)
                binding.emptyLabel.visibility = VISIBLE
            else
                binding.emptyLabel.visibility = GONE
        })

        viewModel.getMovies(adapter)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showSearchFragment() {
        val action = OverviewFragmentDirections.actionOverviewFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun movieDetailFromList(movie: Movie): Unit {
        val action = OverviewFragmentDirections.actionOverviewFragmentToMovieDetailFragment(
                movie.title?:"",
                movie.director?:"",
                movie.year?:"",
                movie.runtime?:"",
                movie.released?:"",
                movie.plot?:"",
                null
        )
        findNavController().navigate(action)
    }
}