package com.example.rssfeed.feedItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.rssfeed.R
import com.example.rssfeed.databinding.FragmentFeedItemBinding

private const val URL = "url"

class FeedItemFragment : Fragment() {
    private var url: String? = null

    private lateinit var binding: FragmentFeedItemBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedItemBinding.inflate(inflater, container, false)
        setupWebView(arguments?.getString("URL").toString())
        return binding.root
    }

    fun setupWebView(url: String) {
        webView = binding.wvBlogPost
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
}