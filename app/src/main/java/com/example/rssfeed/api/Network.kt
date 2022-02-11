package com.example.rssfeed.api

import android.util.Log
import com.example.rssfeed.data.RssItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

private val service: Network by lazy {
    val logging = HttpLoggingInterceptor { message ->
        Log.d("Http logger", message)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://feedforall.com")
        .client(okHttpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    retrofit.create(Network::class.java)
}

fun getNetworkService() = service

interface Network {
    @GET("blog-feed.xml")
    suspend fun fetchRssItems(): List<RssItem>
}