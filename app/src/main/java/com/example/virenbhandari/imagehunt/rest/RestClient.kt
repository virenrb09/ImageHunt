package com.example.virenbhandari.imagehunt.rest

import java.lang.reflect.Type

private const val GET_METHOD = "GET"
private const val POST_METHOD = "POST"

interface RestClient {
    fun performGETApiCall(url: String, listener: IAPIListener<*>, classType : Type)
    fun performPOSTApiCall(url: String, listener: IAPIListener<*>, classType : Type)
}

class RestClientImpl : RestClient {

    override fun performGETApiCall(url: String, listener: IAPIListener<*>, classType : Type) {
        NetworkTask(url, listener, GET_METHOD, classType).execute()
    }

    override fun performPOSTApiCall(url: String, listener: IAPIListener<*>, classType : Type) {
        TODO("implement for making POST api call")
    }

}