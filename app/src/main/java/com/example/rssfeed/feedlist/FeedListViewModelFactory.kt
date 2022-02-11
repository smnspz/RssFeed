package com.example.rssfeed.feedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rssfeed.api.Network
import java.lang.IllegalArgumentException

class FeedListViewModelFactory(private val network: Network) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(FeedListRepository(network)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}