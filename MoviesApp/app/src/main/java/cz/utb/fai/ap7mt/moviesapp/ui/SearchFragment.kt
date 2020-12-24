package cz.utb.fai.ap7mt.moviesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.SearchFragmentBinding

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchButton.setOnClickListener {
            viewModel.searchMovie()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showMovieDetail() {
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
                viewModel.title.value?:"abcd",
                viewModel.director.value?:"abcd",
                viewModel.year.value?:"abcd",
                viewModel.runtime.value?:"abcd"
        )
        findNavController().navigate(action)
    }
}