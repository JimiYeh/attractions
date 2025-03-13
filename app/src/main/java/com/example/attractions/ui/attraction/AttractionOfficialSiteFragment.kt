package com.example.attractions.ui.attraction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.attractions.R
import com.example.attractions.databinding.FragmentAttractionOfficialSiteBinding
import com.example.attractions.ui.MainActivity

class AttractionOfficialSiteFragment : Fragment(R.layout.fragment_attraction_official_site) {

    private var _binding: FragmentAttractionOfficialSiteBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttractionOfficialSiteBinding.bind(view)

        // 設置標題
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.attraction))

        val url = requireArguments().getString(ARG_URL)
            ?: throw IllegalArgumentException("URL must not be null")

        initViews(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(url: String) {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }

    override fun onDestroyView() {
        // 清理 WebView
        binding.webView.apply {
            stopLoading()
            loadUrl("about:blank")
            onPause()
            removeAllViews()
            destroy()
        }
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_URL = "arg_url"

        fun newInstance(url: String) = AttractionOfficialSiteFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_URL, url)
            }
        }
    }
}