package com.example.virenbhandari.imagehunt.ui

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.SearchView
import com.example.virenbhandari.imagehunt.rest.FlickrRepo
import com.example.virenbhandari.imagehunt.rest.IAPIListener
import com.example.virenbhandari.imagehunt.model.ImageData
import com.example.virenbhandari.imagehunt.model.SearchResult
import com.example.virenbhandari.imagehunt.util.FlickrUtil

const val EMPTY_STRING = ""

interface ImageHuntView {
    fun displayState(state: ViewState)
    fun makeToast(msg: String)
}

interface ImageHuntViewModel {
    fun getAdapter(): ImageRecyclerAdapter
    fun getSearchTextListener(): SearchView.OnQueryTextListener
    fun getItemDecorator(): RecyclerView.ItemDecoration
    fun initData()
    fun onRetry()
}

class ImageHuntViewModelImpl(
    context: Context,
    private val view: ImageHuntView,
    private val repo: FlickrRepo,
    private val urlUtil: FlickrUtil
) : ImageHuntViewModel, ImageRecyclerListener, IAPIListener<SearchResult> {

    private var pageCount = 1
    private val adapter = ImageRecyclerAdapter(context, this)
    private var searchTerm = EMPTY_STRING

    override fun initData() {
        if (!TextUtils.isEmpty(searchTerm)) {
            loadMore()
        } else {
            view.displayState(ViewState.EMPTY)
        }
    }

    override fun getAdapter(): ImageRecyclerAdapter {
        return adapter
    }

    override fun getSearchTextListener() = object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String) = false

        override fun onQueryTextSubmit(query: String): Boolean {
            if (query == searchTerm) {
                // do nothing
            } else {
                pageCount = 1
                searchTerm = query.trim()
                loadMore()
            }
            return false
        }
    }

    override fun loadMore() {
        if (searchTerm.isEmpty()) return
        if (pageCount == 1) view.displayState(ViewState.LOADING)
        repo.searchImages(searchTerm, pageCount, this)
    }

    override fun onSuccess(response: SearchResult) {
        if (response.stat == "ok" && response.photos.photo.isNotEmpty()) {
            if (pageCount == 1) view.makeToast("Found " + response.photos.total + " results")
            adapter.updateData(mapData(response.photos.photo), !response.photos.isLastPage(), pageCount == 1)
            pageCount++
            view.displayState(ViewState.COMPLETE)
        } else {
            onApiError()
        }
    }

    override fun onError(errorCode: Int, error: String) {
        onApiError()
    }

    private fun onApiError() {
        if (pageCount == 1) {
            view.displayState(ViewState.ERROR)
        } else {
            adapter.setCanLoadMore(true)
        }
    }

    override fun onRetry() {
        // TODO : add retry func
    }

    private fun mapData(apiData: List<ImageData>): List<ImageItemData> {
        var imageItems = mutableListOf<ImageItemData>()
        apiData.forEach {
            imageItems.add(ImageItemData(it.title, urlUtil.generateUrl(it)))
        }
        return imageItems
    }

    override fun getItemDecorator(): RecyclerView.ItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            outRect.set(8, 8, 8, 8)
        }
    }

}
