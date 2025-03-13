package com.example.attractions.ui.attraction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.attractions.R
import com.example.attractions.databinding.FragmentAttractionDetailBinding
import com.example.attractions.model.attraction.RespAttractionsAll

class AttractionDetailFragment : Fragment(R.layout.fragment_attraction_detail) {

    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttractionDetailBinding.bind(view)

        val attraction = requireArguments().getSerializable(ARG_ATTRACTION) as? RespAttractionsAll.Attraction
            ?: throw IllegalArgumentException("Attraction must not be null")

        setupViews(attraction)
    }

    private fun setupViews(attraction: RespAttractionsAll.Attraction) {
        // TODO: 設置景點詳情頁面的視圖
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ATTRACTION = "arg_attraction"

        fun newInstance(attraction: RespAttractionsAll.Attraction) = AttractionDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_ATTRACTION, attraction)
            }
        }
    }
}