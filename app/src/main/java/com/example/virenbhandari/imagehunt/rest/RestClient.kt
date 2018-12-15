package com.example.virenbhandari.imagehunt.rest

private const val GET_METHOD = "GET"
private const val POST_METHOD = "POST"

interface RestClient<T> {
    fun performGETApiCall(url: String, listener: IAPIListener<T>)
    fun performPOSTApiCall(url: String, listener: IAPIListener<T>)
}

class RestClientImpl<T> : RestClient<T> {

    override fun performGETApiCall(url: String, listener: IAPIListener<T>) {
        NetworkTask(
            url,
            listener,
            GET_METHOD
        ).execute()
    }

    override fun performPOSTApiCall(url: String, listener: IAPIListener<T>) {
        TODO("implement for making POST api call")
    }

}