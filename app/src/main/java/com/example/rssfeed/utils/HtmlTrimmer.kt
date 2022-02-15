package com.example.rssfeed.utils

class HtmlTrimmer {
    companion object {
        fun trimAnchorLink(htmlElement: String): String {
            return htmlElement
                .replace(Regex("<a[^>]+href=\"(.*?)\"[^>]*>(.*?)</a>"), "" )
        }
    }
}