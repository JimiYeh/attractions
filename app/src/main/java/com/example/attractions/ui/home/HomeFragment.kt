package com.example.attractions.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.example.attractions.R
import com.example.attractions.databinding.FragmentHomeBinding
import com.example.attractions.ui.MainActivity
import com.example.attractions.ui.attraction.AttractionDetailFragment
import com.example.attractions.ui.event.EventFragment
import com.example.attractions.ui.home.adapter.AttractionCountAdapter
import com.example.attractions.ui.home.adapter.AttractionsAdapter
import com.example.attractions.ui.home.adapter.EventsAdapter
import com.example.attractions.ui.home.adapter.HeaderAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val attractionCountAdapter by lazy { AttractionCountAdapter() }
    private val eventsHeaderAdapter by lazy { HeaderAdapter(getString(R.string.news_event)) }
    private val eventsAdapter by lazy {
        EventsAdapter(
            onClick = { event ->
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fragment_container, EventFragment.newInstance(event))
                    .addToBackStack(null)
                    .commit()
            }
        )
    }
    private val attractionsHeaderAdapter by lazy { HeaderAdapter(getString(R.string.attraction)) }
    private val attractionsAdapter by lazy {
        AttractionsAdapter(
            onClick = { attraction ->
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fragment_container, AttractionDetailFragment.newInstance(attraction))
                    .addToBackStack(null)
                    .commit()
            }
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // 恢復首頁標題
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.title_home))

        initViews()
        observeViewModel()
    }

    private fun initViews() {
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
            viewModel.isLoading.collect { isLoading ->
                binding.loadingView.root.isVisible = isLoading
            }
        }

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

    override fun onResume() {
        super.onResume()
        // 更新首頁標題
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.title_home))
    }
}