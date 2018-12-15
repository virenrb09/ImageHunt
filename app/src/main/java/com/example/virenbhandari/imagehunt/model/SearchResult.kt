package com.example.virenbhandari.imagehunt.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("stat") val stat: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("photos") val photos: SearchData
)