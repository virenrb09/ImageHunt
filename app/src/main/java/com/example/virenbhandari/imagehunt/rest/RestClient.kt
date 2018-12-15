package com.example.virenbhandari.imagehunt.rest

import com.example.virenbhandari.imagehunt.model.SearchResult

private const val GET_METHOD = "GET"
private const val POST_METHOD = "POST"

interface RestClient {
    fun performGETApiCall(url: String, listener: IAPIListener<SearchResult>)
    fun performPOSTApiCall(url: String, listener: IAPIListener<Any>)
}

class RestClientImpl : RestClient {

    override fun performGETApiCall(url: String, listener: IAPIListener<SearchResult>) {
        NetworkTask(url, listener, GET_METHOD).execute()
    }

    override fun performPOSTApiCall(url: String, listener: IAPIListener<Any>) {
        TODO("implement for making POST api call")
    }

}