package com.example.virenbhandari.imagehunt.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.virenbhandari.imagehunt.R
import com.example.virenbhandari.imagehunt.imageloader.ImageLoader

interface ImageRecyclerListener {
    fun loadMore()
}

class ImageRecyclerAdapter(context: Context, private val listener: ImageRecyclerListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var items = mutableListOf<ImageItemData>()
    private var canLoadMore: Boolean = false
    private var isLoadMoreCalled: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        if (position >= items.size) {
            return LoadingViewHolder(this.layoutInflater.inflate(R.layout.loading_item_view, parent, false))
        } else {
            return ImageViewHolder(this.layoutInflater.inflate(R.layout.image_item_view, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return items.size + if (canLoadMore) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= items.size) {
            if (!isLoadMoreCalled) {
                listener.loadMore()
                isLoadMoreCalled = !isLoadMoreCalled
            }
        } else {
            val displayItem = items[position]
            val displayHolder = holder as ImageViewHolder
            displayHolder.titleTV.text = displayItem.title
            displayHolder.iconIV.tag = position
            ImageLoader.displayImage(displayItem.imageUrl, displayHolder.iconIV, position)
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.title_tv) as TextView
        val iconIV: ImageView = itemView.findViewById(R.id.image_view) as ImageView
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
    }

    fun updateData(items: List<ImageItemData>, canLoadMore: Boolean, shouldClear: Boolean) {
        if (shouldClear) this.items.clear()
        this.isLoadMoreCalled = false
        this.canLoadMore = canLoadMore
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setCanLoadMore(value: Boolean) {
        canLoadMore = value
        notifyDataSetChanged()
    }
}


