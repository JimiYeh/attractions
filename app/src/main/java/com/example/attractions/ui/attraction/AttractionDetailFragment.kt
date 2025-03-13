package com.example.attractions.ui.attraction

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.attractions.R
import com.example.attractions.databinding.FragmentAttractionDetailBinding
import com.example.attractions.model.attraction.RespAttractionsAll.Attraction
import com.example.attractions.ui.MainActivity
import com.example.attractions.ui.attraction.adapter.ImagePagerAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AttractionDetailFragment : Fragment(R.layout.fragment_attraction_detail) {

    companion object {
        private const val ARG_ATTRACTION = "arg_attraction"

        fun newInstance(attraction: Attraction) = AttractionDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_ATTRACTION, attraction)
            }
        }
    }

    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!
    
    private val imageAdapter = ImagePagerAdapter()
    private var autoScrollJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttractionDetailBinding.bind(view)

        val attraction = requireArguments().getSerializable(ARG_ATTRACTION) as? Attraction
            ?: throw IllegalArgumentException("Attraction must not be null")

        // 設置標題
        (requireActivity() as MainActivity).setToolbarTitle(attraction.name)

        setupViews(attraction)
    }

    private fun setupViews(attraction: Attraction) {
        // 設置圖片輪播
        binding.attractionImagePager.apply {
            isVisible = attraction.images.isNotEmpty()
            adapter = imageAdapter
        }
        imageAdapter.submitList(attraction.images)

        // 如果有多張圖片，啟動自動輪播
        if (attraction.images.size > 1) {
            startAutoScroll()
        }

        // 設置其他資訊
        binding.apply {
            // 營業時間
            openTime.apply {
                isVisible = attraction.openTime.isNotEmpty()
                text = attraction.openTime.takeIf { it.isNotEmpty() }?.let { 
                    String.format(getString(R.string.open_time_info), it) 
                }
            }

            // 地址
            address.apply {
                isVisible = attraction.address.isNotEmpty()
                text = attraction.address.takeIf { it.isNotEmpty() }?.let { 
                    String.format(getString(R.string.address_info), it)
                }
                setOnClickListener {
                    if (attraction.address.isNotEmpty()) {
                        // 使用 Google Maps 打開地址
                        val uri = Uri.parse("geo:0,0?q=${Uri.encode(attraction.address)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        
                        try {
                            startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            // 如果沒有安裝 Google Maps，則使用瀏覽器打開
                            val browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(attraction.address)}")
                            val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
                            startActivity(browserIntent)
                        }
                    }
                }
            }

            // 電話
            tel.apply {
                isVisible = attraction.tel.isNotEmpty()
                text = attraction.tel.takeIf { it.isNotEmpty() }?.let { 
                    String.format(getString(R.string.tel_info), it)
                }
                setOnClickListener {
                    if (attraction.tel.isNotEmpty()) {
                        // 撥打電話
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${attraction.tel}")
                        }
                        startActivity(intent)
                    }
                }
            }

            // 網址
            officialSite.apply {
                isVisible = attraction.officialSite.isNotEmpty()
                text = attraction.officialSite.takeIf { it.isNotEmpty() }?.let { 
                    String.format(getString(R.string.official_site_info), it)
                }
                setOnClickListener {
                    if (attraction.officialSite.isNotEmpty()) {
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                AttractionOfficialSiteFragment.newInstance(attraction.officialSite)
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }

            introduction.text = attraction.introduction
        }
    }

    private fun startAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
                delay(5000) // 延遲 5 秒
                val currentItem = binding.attractionImagePager.currentItem
                val nextItem = if (currentItem < (imageAdapter.itemCount - 1)) currentItem + 1 else 0
                binding.attractionImagePager.setCurrentItem(nextItem, true)
            }
        }
    }

    override fun onDestroyView() {
        autoScrollJob?.cancel()
        autoScrollJob = null
        super.onDestroyView()
        _binding = null
    }
}