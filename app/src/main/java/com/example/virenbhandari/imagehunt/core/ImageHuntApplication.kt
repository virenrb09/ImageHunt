package com.example.virenbhandari.imagehunt.core

import android.app.Application
import android.content.Context

class ImageHuntApplication : Application() {

    private var sContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        sContext = this
    }

}