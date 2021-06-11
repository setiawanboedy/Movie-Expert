package com.attafakkur.movieexpert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.utils.Constants.IMAGE_URL
import com.attafakkur.core.utils.hidePb
import com.attafakkur.core.utils.showPb
import com.attafakkur.core.utils.snack
import com.attafakkur.movieexpert.MainActivity
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.DetailkFragmentBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: DetailkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailkFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val movieId = args.movieData
        populateDetailMovie(movieId)
    }

    private fun populateDetailMovie(movieId: Int) {
        viewModel.getDetailMovie(movieId).observe(viewLifecycleOwner, { detail ->
            run {
                when (detail) {
                    is State.Loading -> {
                        binding.pbDetail.showPb()
                        showNav(true)
                    }
                    is State.Success -> {
                        setFavoriteMovie(detail.data?.isFavorite)
                        populateDataDetail(detail.data)
                        binding.pbDetail.hidePb()
                        showNav(false)
                        binding.favFloat.setOnClickListener {
                            detail.data?.let { it1 -> viewModel.setFavoriteMovie(it1) }
                            setFavoriteMovie(detail.data?.isFavorite)

                        }
                    }
                    is State.Error -> {
                        detail.message?.let { binding.root.snack(it) }
                        binding.pbDetail.hidePb()
                        showNav(false)
                    }
                    else -> binding.pbDetail.hidePb()
                }
            }
        })
    }

    private fun populateDataDetail(data: Movie?) {
        with(binding) {
            collaps.title = data?.title
            tvOverview.text = data?.overview
            ratingS.text = resources.getString(R.string.rating_d, data?.vote)
            ratingBar.rating = data?.vote?.toFloat() ?: 0f

            Glide.with(requireActivity())
                .load(IMAGE_URL + data?.backDrop)
                .into(backdrop)

        }
    }

    private fun showNav(isVisible: Boolean) {
        binding.appBar.isVisible = !isVisible
        binding.scrollNst.isVisible = !isVisible
    }

    private fun setFavoriteMovie(favorite: Boolean?) {
        if (favorite == true) {
            binding.favFloat.setImageDrawable(
                getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_filled
                )
            )
        } else {
            binding.favFloat.setImageDrawable(
                getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.hide()
    }

}