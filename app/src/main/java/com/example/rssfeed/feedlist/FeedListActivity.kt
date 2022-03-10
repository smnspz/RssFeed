package com.example.rssfeed.feedlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssfeed.R
import com.example.rssfeed.api.getNetworkService
import com.example.rssfeed.data.RssFeed
import com.example.rssfeed.data.RssItem
import com.example.rssfeed.databinding.ActivityFeedListBinding
import com.example.rssfeed.feedItem.FeedItemFragment
import com.example.rssfeed.utils.Status

class FeedListActivity : AppCompatActivity() {

    private lateinit var adapter: FeedListAdapter
    private lateinit var viewModel: FeedListViewModel
    private lateinit var binding: ActivityFeedListBinding
    private val fragment: FeedItemFragment = FeedItemFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewModel()
        setupUI()
    }

    override fun onStart() {
        setupObserver()
        super.onStart()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, FeedListViewModelFactory(getNetworkService()))
            .get(FeedListViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FeedListAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter
        adapter.registerListener(object:OnButtonClick {
            override fun onClickListener(url: String) {
                setupFragment(url)
            }
        })
    }

    private fun retrieveList(rssItems: List<RssItem>) {
        adapter.apply {
            addRssItems(rssItems)
            notifyDataSetChanged()
        }
    }

    fun setupFragment(url: String) {
        val bundle = Bundle()
        bundle.putString("URL", url)
        fragment.arguments = bundle

        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupObserver() {
        viewModel.getFeedList()
        viewModel.feedList.observe(this, Observer {
            when (it) {
                is Status.Success -> {
                    changeViewBasedOnStatus(it)
                }
                is Status.Error -> {
                    changeViewBasedOnStatus(it)
                }
                is Status.Loading -> {
                    showSpinnerOnLoading()
                }
            }
        })
    }

    private fun changeViewBasedOnStatus(status: Status<RssFeed>) {
        if (status is Status.Success) {
            binding.loadingSpinner.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            if (status is Status.Error) {
                Log.e(status.message, "Error inside activity")
                Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                return
            }
            retrieveList(status.data.channel?.item ?: listOf())
        }
    }

    private fun showSpinnerOnLoading() {
        if (viewModel.hasLoadedRssFeed) {
            binding.loadingSpinner.visibility = View.GONE
        } else {
            binding.loadingSpinner.visibility = View.VISIBLE
        }
    }
}