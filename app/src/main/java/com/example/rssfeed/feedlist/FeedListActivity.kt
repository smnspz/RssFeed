package com.example.rssfeed.feedlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssfeed.api.getNetworkService
import com.example.rssfeed.data.RssItem
import com.example.rssfeed.databinding.ActivityFeedListBinding
import com.example.rssfeed.utils.Status


class FeedListActivity : AppCompatActivity() {

    private lateinit var adapter: FeedListAdapter
    private lateinit var viewModel: FeedListViewModel
    private lateinit var binding: ActivityFeedListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, FeedListViewModelFactory(getNetworkService()))
            .get(FeedListViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FeedListAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter
    }

    private fun retrieveList(rssItems: List<RssItem>) {
        adapter.apply {
            addRssItems(rssItems)
            notifyDataSetChanged()
        }
    }

    private fun setupObserver() {
        viewModel.getFeedList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        resource.data?.let { rssItems -> retrieveList(rssItems) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.e(it.message, "Error Inside activity")
                    }
                    Status.LOADING -> {
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }
}