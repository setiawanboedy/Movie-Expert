package com.attafakkur.movieexpert.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.ui.MovieAdapter
import com.attafakkur.core.utils.*
import com.attafakkur.movieexpert.MainActivity
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.HomeFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModels()

    private val binding by viewBinding(HomeFragmentBinding::bind)
    private var movieAdapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(object : OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(action)
            }
        })

        populateMovies(binding)
        refresh(binding.homeRefresh) {
            populateMovies(binding)
        }
    }

    private fun populateMovies(binding: HomeFragmentBinding) {
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
        binding.rvMovie.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvMovie.adapter = movieAdapter
        binding.rvMovie.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
        val colorDrawable = ColorDrawable(Color.parseColor("#1C1F30"))
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(colorDrawable)
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
    }

}