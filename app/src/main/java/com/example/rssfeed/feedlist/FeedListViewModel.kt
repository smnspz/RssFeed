package com.example.rssfeed.feedlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.RssFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedListViewModel(private val repository: FeedListRepository) : ViewModel() {

    private var _hasLoadedRssFeed: Boolean = false
    val hasLoadedRssFeed: Boolean = _hasLoadedRssFeed

    private var _feedList: MutableLiveData<Result<RssFeed>> = MutableLiveData()
    val feedList = _feedList

    fun getFeedList() = viewModelScope.launch {

        _feedList.postValue(Result.Loading())
        _hasLoadedRssFeed = false

        try {
            _feedList.postValue(Result.Success(repository.getFeed()))
            _hasLoadedRssFeed = true

        } catch (exception: Exception) {
            _feedList.postValue(Result.Error(exception.message ?: "Error while fetching feedList"))
        }
    }

    sealed class Result<T> {
        class Success<T>(val data: RssFeed) : Result<T>()
        class Error<T>(val message: String) : Result<T>()
        class Loading<T>: Result<T>()
    }
}



