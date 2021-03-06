package cz.utb.fai.ap7mt.moviesapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.MovieDetailFragmentBinding

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var binding: MovieDetailFragmentBinding
    private lateinit var viewModelFactory: MovieDetailViewModelFactory
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)

        val movieDetailArgs = MovieDetailFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = MovieDetailViewModelFactory(
                movieDetailArgs.title,
                movieDetailArgs.year,
                movieDetailArgs.director,
                movieDetailArgs.runtime,
                movieDetailArgs.released,
                movieDetailArgs.plot,
                movieDetailArgs.errorMessage
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.overviewButton.setOnClickListener {
            showOverview()
        }

        if (viewModel.errorMessage.value != null){
            binding.detailLayout.visibility = GONE
            binding.titleAndYearLabel.visibility = GONE
            binding.detailErrorMessage.visibility = VISIBLE
            binding.detailErrorMessage.text = viewModel.errorMessage.value
        }
        else {
            binding.detailLayout.visibility = VISIBLE
            binding.titleAndYearLabel.visibility = VISIBLE
            binding.detailErrorMessage.visibility = GONE
            binding.detailErrorMessage.text = ""
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun showOverview(){
        val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToOverviewFragment()
        findNavController().navigate(action)
    }
}