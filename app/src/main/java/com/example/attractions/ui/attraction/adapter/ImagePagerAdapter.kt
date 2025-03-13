package com.example.attractions.ui.attraction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.attractions.databinding.ItemImageBinding
import com.example.attractions.model.attraction.RespAttractionsAll.Attraction.Image

class ImagePagerAdapter : RecyclerView.Adapter<ImagePagerAdapter.ViewHolder>() {
    private var images: List<Image> = emptyList()

    fun submitList(newImages: List<Image>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            Glide.with(binding.image)
                .load(image.src)
                .centerCrop()
                .into(binding.image)
        }
    }
} 