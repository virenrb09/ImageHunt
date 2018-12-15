package com.example.virenbhandari.imagehunt.util

import com.example.virenbhandari.imagehunt.core.IMAGE_URL_PATTERN
import com.example.virenbhandari.imagehunt.model.ImageData
import com.example.virenbhandari.imagehunt.ui.ImageItemData

interface FlickrUtil {
    fun generateUrl(imageData: ImageData): String
    fun mapData(apiData: List<ImageData>): List<ImageItemData>
}

class FlickrUtilImpl : FlickrUtil {
    override fun generateUrl(imageData: ImageData): String {
        return String.format(IMAGE_URL_PATTERN, imageData.farm, imageData.server, imageData.id, imageData.secret)
    }

    override fun mapData(apiData: List<ImageData>): List<ImageItemData> {
        var imageItems = mutableListOf<ImageItemData>()
        apiData.forEach {
            imageItems.add(ImageItemData(it.title, generateUrl(it)))
        }
        return imageItems
    }
}