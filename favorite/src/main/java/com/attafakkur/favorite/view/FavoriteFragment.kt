package com.attafakkur.favorite.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.ui.MovieAdapter
import com.attafakkur.core.utils.OnItemClickCallback
import com.attafakkur.favorite.DaggerFavoriteComponent
import com.attafakkur.favorite.R
import com.attafakkur.favorite.ViewModelFactory
import com.attafakkur.favorite.databinding.FavoriteFragmentBinding
import com.attafakkur.movieexpert.di.FavoriteModuleDependencies
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var favBinding: FavoriteFragmentBinding? = null
    private val binding get() = favBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favBinding = FavoriteFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
                .context(context)
                .appDependencies(
                        EntryPointAccessors.fromApplication(context.applicationContext, FavoriteModuleDependencies::class.java)
                )
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieItemClick()
        populateFavoriteMovie()
        binding?.deleteFloat?.setOnClickListener { deleteAllFav() }
    }

    private fun populateFavoriteMovie() {
        viewModel.favMovies.observe(viewLifecycleOwner, { fav ->
            if (fav.isEmpty()) {
                binding?.noFav?.isVisible = true
                binding?.rvFav?.adapter.let { adapter ->
                    when (adapter) {
                        is MovieAdapter -> adapter.setData(fav)
                    }
                }
            } else {
                binding?.noFav?.isVisible = false
                binding?.rvFav?.adapter.let { adapter ->
                    when (adapter) {
                        is MovieAdapter -> adapter.setData(fav)
                    }
                }
            }
        })

            binding?.rvFav?.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding?.rvFav?.adapter = movieAdapter
            binding?.rvFav?.setHasFixedSize(true)

    }

    private fun movieItemClick() {
        movieAdapter = MovieAdapter(object : OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(data.id)
                findNavController().navigate(action)
            }

        })
    }

    private fun deleteAllFav() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.title_del))
                .setMessage(resources.getString(R.string.message))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    viewModel.deleteAll()
                }
                .show()
    }
}