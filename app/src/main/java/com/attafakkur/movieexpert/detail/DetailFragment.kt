package com.attafakkur.movieexpert.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.utils.hidePb
import com.attafakkur.core.utils.showPb
import com.attafakkur.core.utils.snack
import com.attafakkur.movieexpert.BuildConfig.IMAGE_URL
import com.attafakkur.movieexpert.MainActivity
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.DetailFragmentBinding
import com.bumptech.glide.Glide
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()
    private val binding by viewBinding(DetailFragmentBinding::bind)

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
        binding.collapsing.title = data?.title
        binding.tvOverview.text = data?.overview
        binding.ratingS.text = resources.getString(R.string.rating_d, data?.vote)
        binding.ratingBar.rating = data?.vote?.toFloat() ?: 0f

        binding.backdrop.let {
            Glide.with(requireActivity())
                .load(IMAGE_URL + data?.backDrop)
                .into(it)
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

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.show()
    }
}