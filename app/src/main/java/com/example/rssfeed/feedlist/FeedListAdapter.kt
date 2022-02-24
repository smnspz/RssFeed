package com.example.rssfeed.feedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.data.RssItem
import com.example.rssfeed.databinding.RowFeedItemBinding
import com.example.rssfeed.feedItem.FeedItemFragment
import com.example.rssfeed.utils.HtmlTrimmer

class FeedListAdapter(private val rssItems: MutableList<RssItem>) :

    RecyclerView.Adapter<FeedListAdapter.DataViewHolder>() {

    var setupFragment: ((url: String) -> Unit)? = null

    class DataViewHolder(private val binding: RowFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val moreButton = binding.btnMore

        fun bind(rssItem: RssItem) {
            val trimmedHtml = HtmlTrimmer
                .trimAnchorLink(rssItem.description ?: "")
            itemView.apply {
                binding.tvRssTitle.text = rssItem.title
                binding.tvRssDescription.text = HtmlCompat.fromHtml(trimmedHtml, 0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(rssItems[position])
        holder.moreButton.setOnClickListener {
            rssItems.getOrNull(position)?.link?.let {
                setupFragment?.invoke(it)
            }
        }
    }

    override fun getItemCount(): Int = rssItems.size

    fun addRssItems(rssItems: List<RssItem>) {
        this.rssItems.apply {
            clear()
            addAll(rssItems)
        }
    }
}