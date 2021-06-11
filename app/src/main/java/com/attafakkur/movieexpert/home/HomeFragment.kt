package com.attafakkur.movieexpert.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.ui.MovieAdapter
import com.attafakkur.core.utils.OnItemClickCallback
import com.attafakkur.core.utils.hidePb
import com.attafakkur.core.utils.showPb
import com.attafakkur.core.utils.snack
import com.attafakkur.movieexpert.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var homeBinding: HomeFragmentBinding? = null
    private val binding get() = homeBinding as HomeFragmentBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        homeBinding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(object : OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(action)
            }

        })

        populateMovies()
    }

    private fun populateMovies() {
        viewModel.movie.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie) {
                    is State.Loading -> {
                        binding.pbMovie.showPb()
                        binding.rvMovie.isVisible = false
                    }
                    is State.Success -> {
                        binding.pbMovie.hidePb()
                        binding.rvMovie.isVisible = true
                        binding.rvMovie.adapter.let { adapter ->
                            when (adapter) {
                                is MovieAdapter -> adapter.setData(movie.data)
                            }
                        }
                    }
                    is State.Error -> {
                        binding.pbMovie.hidePb()
                        binding.rvMovie.isVisible = true
                        movie.message?.let { binding.root.snack(it) }
                    }
                    is State.Empty -> {
                        binding.pbMovie.hidePb()
                        binding.rvMovie.isVisible = true
                    }
                }
            }
        })
        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

}