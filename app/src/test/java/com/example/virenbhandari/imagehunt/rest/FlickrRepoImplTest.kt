package com.example.virenbhandari.imagehunt.rest

import com.example.virenbhandari.imagehunt.core.FLICKR_API_KEY
import com.example.virenbhandari.imagehunt.core.FLICKR_IMAGE_SEARCH_URL
import com.example.virenbhandari.imagehunt.core.PER_PAGE_COUNT
import com.example.virenbhandari.imagehunt.model.SearchResult
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FlickrRepoImplTest {

    lateinit var flickrRepo: FlickrRepo
    @Mock
    lateinit var restClient: RestClient

    @Before
    fun setup() {
        flickrRepo = FlickrRepoImpl(restClient)
    }

    @Test
    fun searchImages() {
        val key = "keyword"
        val url = String.format(FLICKR_IMAGE_SEARCH_URL, FLICKR_API_KEY, key, 1, PER_PAGE_COUNT)
        val listener = responseListener()
        flickrRepo.searchImages(key, 1, listener)
        Mockito.verify(restClient).performGETApiCall(url, listener)
    }

    @Test
    fun searchImages_withUrlEncoding() {
        val key = "keyword secondkeyword"
        val encodedKey = "keyword+secondkeyword"
        val url = String.format(FLICKR_IMAGE_SEARCH_URL, FLICKR_API_KEY, encodedKey, 1, PER_PAGE_COUNT)
        val listener = responseListener()
        flickrRepo.searchImages("keyword secondkeyword", 1, listener)
        Mockito.verify(restClient).performGETApiCall(url, listener)
    }

    private fun responseListener() = object : IAPIListener<SearchResult> {
        override fun onSuccess(response: SearchResult) {
        }

        override fun onError(errorCode: Int, error: String) {
        }
    }
}