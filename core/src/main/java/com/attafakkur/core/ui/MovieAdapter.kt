package com.attafakkur.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attafakkur.core.R

import com.attafakkur.core.databinding.ItemListBinding
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.utils.Constants.IMAGE_URL
import com.attafakkur.core.utils.OnItemClickCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class MovieAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var list = ArrayList<Movie>()


    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        list.clear()
        list.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                val apply = RequestOptions()
                    .override(poster.width, poster.height)

                Glide.with(itemView.context)
                    .load(IMAGE_URL + data.poster)
                    .apply(apply)
                    .error(R.drawable.ic_action_err)
                    .into(poster)

                title.text = data.title
                release.text = data.release
                rating.text = itemView.resources.getString(R.string.rating_d, data.vote)
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(list[adapterPosition])
            }
        }
    }

}