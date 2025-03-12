package com.example.attractions.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.databinding.ItemAttractionBinding
import com.example.attractions.model.attraction.RespAttractionsAll

class AttractionsAdapter : PagingDataAdapter<RespAttractionsAll.Attraction, AttractionsAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttractionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(
        private val binding: ItemAttractionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: RespAttractionsAll.Attraction) {
            binding.name.text = attraction.name
            // TODO: 設置其他景點相關資訊
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