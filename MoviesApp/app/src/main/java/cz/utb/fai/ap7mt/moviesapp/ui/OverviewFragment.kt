package cz.utb.fai.ap7mt.moviesapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ap7mt.moviesapp.R
import cz.utb.fai.ap7mt.moviesapp.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var binding: OverviewFragmentBinding
    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchFragmentButton.setOnClickListener {
            showSearchFragment()
        }

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
}