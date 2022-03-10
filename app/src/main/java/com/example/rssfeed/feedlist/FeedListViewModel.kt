package com.example.rssfeed.feedlist

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rssfeed.data.RssFeed
import com.example.rssfeed.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedListViewModel(private val repository: FeedListRepository) : ViewModel() {

    private var _hasLoadedRssFeed: Boolean = false
    val hasLoadedRssFeed: Boolean =  _hasLoadedRssFeed

    fun getFeedList() = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        _hasLoadedRssFeed = false
        try {
            emit(Result.Success(repository.getFeed()))
            Log.d(repository.getFeed().channel?.item.toString(), "RssFeed item")
            _hasLoadedRssFeed = true
        } catch (exception: Exception) {
            emit(Result.Error(exception.message ?: "Error occurred"))
        }
    }

    sealed class Result {
        class Success(val rssFeed: RssFeed?) : Result()
        class Error(val errorMessage: String) : Result()
        object Loading : Result()
    }
}



