package com.example.rssfeed.feedlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.RssFeed
import com.example.rssfeed.utils.Status
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedListViewModel(private val repository: FeedListRepository) : ViewModel() {

    private var _hasLoadedRssFeed: Boolean = false
    val hasLoadedRssFeed: Boolean = _hasLoadedRssFeed

    private var _feedList: MutableLiveData<Status<RssFeed>> = MutableLiveData()
    val feedList = _feedList

    fun getFeedList() = viewModelScope.launch {

        _feedList.postValue(Status.Loading())
        _hasLoadedRssFeed = false

        try {
            _feedList.postValue(Status.Success(repository.getFeed()))
            _hasLoadedRssFeed = true

        } catch (exception: Exception) {
            _feedList.postValue(Status.Error(exception.message ?: "Error while fetching feedList"))
        }
    }


}



