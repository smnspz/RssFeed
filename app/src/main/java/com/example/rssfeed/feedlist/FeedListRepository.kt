package com.example.rssfeed.feedlist

import com.example.rssfeed.api.Network

class FeedListRepository(private val network: Network) {

    suspend fun getFeed() = network.fetchRssItems()
}