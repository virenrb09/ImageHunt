package com.example.virenbhandari.imagehunt.core


/*
   Image search url format with paging and text based search
    https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens&page=1&per_page=5
*/

const val FLICKR_API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736"
const val FLICKR_IMAGE_SEARCH_URL =
    "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&format=json&nojsoncallback=1&safe_search=1&text=%s&page=%d&per_page=%d"
const val PER_PAGE_COUNT = 10
const val IMAGE_URL_PATTERN = "http://farm%d.static.flickr.com/%s/%s_%s.jpg"