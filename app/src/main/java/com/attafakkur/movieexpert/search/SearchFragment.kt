package com.attafakkur.movieexpert.search

import android.animation.Animator
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.ui.MovieAdapter
import com.attafakkur.core.utils.OnItemClickCallback
import com.attafakkur.core.utils.hidePb
import com.attafakkur.core.utils.showPb
import com.attafakkur.core.utils.snack
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var searchBinding: SearchFragmentBinding? = null
    private val binding get() = searchBinding as SearchFragmentBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieItemClick()
        populateSearchMovie()

        binding.splashLottie.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}

        })

        showSearchMovie(true)
    }

    private fun movieItemClick() {
        movieAdapter = MovieAdapter(object : OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(data.id)
                findNavController().navigate(action)
            }

        })
    }

    private fun populateSearchMovie() {
        setHasOptionsMenu(true)
        binding.pbSearch.hidePb()
        viewModel.movie.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie) {
                    is State.Loading -> binding.pbSearch.showPb()
                    is State.Success -> {
                        Log.e("search", movie.data.toString())
                        binding.pbSearch.hidePb()
                        binding.rvSearch.adapter.let { adapter ->
                            when (adapter) {
                                is MovieAdapter -> adapter.setData(movie.data)
                            }
                        }
                    }
                    is State.Error -> {
                        showNoMovie(true)
                        binding.pbSearch.hidePb()
                        movie.message?.let { binding.root.snack(it) }
                        Log.e("search", movie.message.toString())
                    }
                    is State.Empty -> binding.pbSearch.hidePb()
                }
            }
        })
        with(binding.rvSearch) {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.app_bar_search).actionView as SearchView

        search.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        search.queryHint = "Search movie"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showSearchMovie(false)
                showNoMovie(false)
                binding.pbSearch.showPb()
                if (query != null) {
                    lifecycleScope.launch {
                        viewModel.searchMovies(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun showNoMovie(state: Boolean) {
        binding.noMovie.isVisible = state
        binding.rvSearch.isVisible = !state
    }

    private fun showSearchMovie(state: Boolean) {
        binding.splashLottie.isVisible = state
        binding.rvSearch.isVisible = !state
    }

    override fun onDestroy() {
        super.onDestroy()
        searchBinding = null
    }

}