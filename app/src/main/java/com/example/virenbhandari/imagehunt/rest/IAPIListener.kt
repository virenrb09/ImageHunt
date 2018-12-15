package com.example.virenbhandari.imagehunt.rest

interface IAPIListener<T> {
    fun onSuccess(response: T)
    fun onError(errorCode: Int, error: String)
}