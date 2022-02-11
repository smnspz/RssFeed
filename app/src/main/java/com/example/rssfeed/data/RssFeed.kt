package com.example.rssfeed.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @field:Element
    var channel: RssChannel? = null
)

@Root(name = "channel", strict = false)
data class RssChannel @JvmOverloads constructor(
    @field:ElementList(inline = true, required = false)
    var item: List<RssItem>? = null
)

@Root(name = "item", strict = false)
data class RssItem @JvmOverloads constructor(
    @field:Element
    var title: String? = "",

    @field:Element
    var description: String? = "",

    @field:Element
    var link: String? = "",

    @field:Element
    var pubDate: String? = ""
)