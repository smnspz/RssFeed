package com.example.rssfeed.feedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.data.RssItem
import com.example.rssfeed.databinding.RowFeedItemBinding

class FeedListAdapter(private val rssItems: MutableList<RssItem>) :
    RecyclerView.Adapter<FeedListAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    class DataViewHolder(private val binding: RowFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rssItem: RssItem) {
            itemView.apply {
                binding.tvRssTitle.text = rssItem.title
                binding.tvRssDescription.text = HtmlCompat.fromHtml(rssItem.description ?: "", 0)
            }
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(rssItems[position])
    }

    override fun getItemCount(): Int = rssItems.size

    fun addRssItems(rssItems: List<RssItem>) {
        this.rssItems.apply {
            clear()
            addAll(rssItems)
        }
    }
}