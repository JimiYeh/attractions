package com.example.attractions.ui.event

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.attractions.R
import com.example.attractions.databinding.FragmentEventBinding
import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.ui.MainActivity

class EventFragment : Fragment(R.layout.fragment_event) {

    companion object {
        private const val ARG_EVENT = "arg_event"

        fun newInstance(event: RespEventsNews.Event) = EventFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_EVENT, event)
            }
        }
    }

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventBinding.bind(view)

        // 設置標題
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.news_event))

        val event = requireArguments().getSerializable(ARG_EVENT) as? RespEventsNews.Event
            ?: throw IllegalArgumentException("Event must not be null")

        initViews(event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(event: RespEventsNews.Event) {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.loadingView.root.isVisible = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.loadingView.root.isVisible = false
                }
            }
            loadUrl(event.url)
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
} 