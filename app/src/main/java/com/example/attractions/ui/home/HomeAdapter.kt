package com.example.attractions.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.databinding.ItemAttractionBinding
import com.example.attractions.databinding.ItemHeaderBinding
import com.example.attractions.databinding.ItemNewsEventBinding
import com.example.attractions.model.HomeItem

class HomeAdapter : PagingDataAdapter<HomeItem, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.AttractionCount -> VIEW_TYPE_ATTRACTION_COUNT
            is HomeItem.Header -> VIEW_TYPE_HEADER
            is HomeItem.NewsEventItem -> VIEW_TYPE_EVENT
            is HomeItem.AttractionItem -> VIEW_TYPE_ATTRACTION
            null -> throw IllegalStateException("Unknown view type at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ATTRACTION_COUNT -> AttractionCountViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_EVENT -> EventViewHolder(
                ItemNewsEventBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_ATTRACTION -> AttractionViewHolder(
                ItemAttractionBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is AttractionCountViewHolder -> holder.bind(item as HomeItem.AttractionCount)
            is HeaderViewHolder -> holder.bind(item as HomeItem.Header)
            is EventViewHolder -> holder.bind(item as HomeItem.NewsEventItem)
            is AttractionViewHolder -> holder.bind(item as HomeItem.AttractionItem)
        }
    }

    class AttractionCountViewHolder(
        private val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.AttractionCount) {
            binding.headerTitle.text = "台北共有 ${item.count} 個景點"
        }
    }

    class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.Header) {
            binding.headerTitle.text = item.title
        }
    }

    class EventViewHolder(
        private val binding: ItemNewsEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.NewsEventItem) {
            binding.title.text = item.event.title
            // TODO: 設置其他事件相關資訊
        }
    }

    class AttractionViewHolder(
        private val binding: ItemAttractionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.AttractionItem) {
            binding.name.text = item.attraction.name
            // TODO: 設置其他景點相關資訊
        }
    }

    companion object {
        private const val VIEW_TYPE_ATTRACTION_COUNT = 0
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_EVENT = 2
        private const val VIEW_TYPE_ATTRACTION = 3

        private val COMPARATOR = object : DiffUtil.ItemCallback<HomeItem>() {
            override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem == newItem
            }
        }
    }
} 