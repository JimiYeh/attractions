package com.example.attractions.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.databinding.ItemAttractionCountBinding
import com.example.attractions.databinding.ItemHeaderBinding

class AttractionCountAdapter : RecyclerView.Adapter<AttractionCountAdapter.ViewHolder>() {
    private var count: Int = 0

    fun setCount(count: Int) {
        this.count = count
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttractionCountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(count)
    }

    override fun getItemCount(): Int = 1

    class ViewHolder(
        private val binding: ItemAttractionCountBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(count: Int) {
            binding.attractionCount.text = "台北共有 $count 個景點"
        }
    }
} 