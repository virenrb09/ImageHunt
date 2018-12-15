package com.example.virenbhandari.imagehunt.model

import com.google.gson.annotations.SerializedName

data class SearchData(
    @SerializedName("page") val page: Long,
    @SerializedName("pages") val pages: Long,
    @SerializedName("perpage") val perPage: Long,
    @SerializedName("total") val total: String,
    @SerializedName("photo") val photo: List<ImageData>
) {
    fun isLastPage() = page == pages
}