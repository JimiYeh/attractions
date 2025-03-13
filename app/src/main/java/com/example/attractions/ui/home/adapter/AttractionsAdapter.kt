package com.example.attractions.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.attractions.databinding.ItemAttractionBinding
import com.example.attractions.model.attraction.RespAttractionsAll

class AttractionsAdapter(private val onClick: (RespAttractionsAll.Attraction) -> Unit) : PagingDataAdapter<RespAttractionsAll.Attraction, AttractionsAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttractionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(
        private val binding: ItemAttractionBinding,
        private val onClick: (RespAttractionsAll.Attraction) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: RespAttractionsAll.Attraction) {
            binding.root.setOnClickListener {
                onClick(attraction)
            }
            binding.name.text = attraction.name
            binding.image.isVisible = attraction.images.isNotEmpty()
            if (attraction.images.isNotEmpty()) {
                Glide.with(binding.image)
                    .load(attraction.images[0].src)
                    .centerCrop()
                    .into(binding.image)
            }
            binding.introduction.text = attraction.introduction
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RespAttractionsAll.Attraction>() {
            override fun areItemsTheSame(oldItem: RespAttractionsAll.Attraction, newItem: RespAttractionsAll.Attraction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RespAttractionsAll.Attraction, newItem: RespAttractionsAll.Attraction): Boolean {
                return oldItem == newItem
            }
        }
    }
} 