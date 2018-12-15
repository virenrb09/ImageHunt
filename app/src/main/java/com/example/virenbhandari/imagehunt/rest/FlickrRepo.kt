package com.example.virenbhandari.imagehunt.rest

import com.example.virenbhandari.imagehunt.core.FLICKR_API_KEY
import com.example.virenbhandari.imagehunt.core.FLICKR_IMAGE_SEARCH_URL
import com.example.virenbhandari.imagehunt.core.PER_PAGE_COUNT
import com.example.virenbhandari.imagehunt.model.SearchResult
import com.google.gson.reflect.TypeToken
import java.net.URLEncoder


interface FlickrRepo {
    fun searchImages(keyword: String, page: Int, listener: IAPIListener<SearchResult>)
}

class FlickrRepoImpl(private val restClient: RestClient) : FlickrRepo {

    override fun searchImages(keyword: String, page: Int, listener: IAPIListener<SearchResult>) {
        val url = String.format(FLICKR_IMAGE_SEARCH_URL, FLICKR_API_KEY, URLEncoder.encode(keyword, "UTF-8"), page, PER_PAGE_COUNT)
        restClient.performGETApiCall(url, listener, object : TypeToken<SearchResult>() {}.type)
    }

}