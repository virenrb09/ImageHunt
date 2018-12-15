package com.example.virenbhandari.imagehunt.util

import com.example.virenbhandari.imagehunt.core.IMAGE_URL_PATTERN
import com.example.virenbhandari.imagehunt.model.ImageData


class FlickrUtil {
    fun generateUrl(imageData: ImageData): String {
        return String.format(IMAGE_URL_PATTERN, imageData.farm, imageData.server, imageData.id, imageData.secret)
    }
}