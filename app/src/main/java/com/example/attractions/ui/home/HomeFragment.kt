package com.example.attractions.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.example.attractions.R
import com.example.attractions.databinding.FragmentHomeBinding
import com.example.attractions.ui.home.adapter.AttractionCountAdapter
import com.example.attractions.ui.home.adapter.AttractionsAdapter
import com.example.attractions.ui.home.adapter.EventsAdapter
import com.example.attractions.ui.home.adapter.HeaderAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val attractionCountAdapter = AttractionCountAdapter()
    private val eventsHeaderAdapter = HeaderAdapter("最新消息")
    private val eventsAdapter = EventsAdapter()
    private val attractionsHeaderAdapter = HeaderAdapter("遊憩景點")
    private val attractionsAdapter = AttractionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = ConcatAdapter(
            attractionCountAdapter,
            eventsHeaderAdapter,
            eventsAdapter,
            attractionsHeaderAdapter,
            attractionsAdapter
        )
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collectLatest { pagingData ->
                eventsAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.attractions.collectLatest { pagingData ->
                attractionsAdapter.submitData(pagingData)
            }
        }

        viewModel.attractionsCount.observe(viewLifecycleOwner) { count ->
            attractionCountAdapter.setCount(count)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}