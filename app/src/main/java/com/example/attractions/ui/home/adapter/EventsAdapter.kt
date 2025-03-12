package com.example.attractions.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.databinding.ItemNewsEventBinding
import com.example.attractions.model.event.RespEventsNews

class EventsAdapter : PagingDataAdapter<RespEventsNews.Event, EventsAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsEventBinding.inflate(
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
        private val binding: ItemNewsEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: RespEventsNews.Event) {
            binding.title.text = event.title
            binding.description.text = event.description
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RespEventsNews.Event>() {
            override fun areItemsTheSame(oldItem: RespEventsNews.Event, newItem: RespEventsNews.Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RespEventsNews.Event, newItem: RespEventsNews.Event): Boolean {
                return oldItem == newItem
            }
        }
    }
} 