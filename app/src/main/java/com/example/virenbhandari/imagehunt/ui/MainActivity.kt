package com.example.virenbhandari.imagehunt.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.virenbhandari.imagehunt.R
import com.example.virenbhandari.imagehunt.rest.FlickrRepoImpl
import com.example.virenbhandari.imagehunt.rest.RestClientImpl
import com.example.virenbhandari.imagehunt.util.FlickrUtilImpl

class MainActivity : AppCompatActivity(), ImageHuntView {

    private val searchView: SearchView                  by lazy { findViewById<SearchView>(R.id.searchView) }
    private val imageRV: RecyclerView                   by lazy { findViewById<RecyclerView>(R.id.image_rv) }
    private val emptyView: LinearLayout                 by lazy { findViewById<LinearLayout>(R.id.empty_view) }
    private val errorView: LinearLayout                 by lazy { findViewById<LinearLayout>(R.id.error_view) }
    private val progressBar: ProgressBar                by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    private lateinit var viewModel: ImageHuntViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // instead of creating deps here, we can use dagger for providing dependencies
        viewModel = ImageHuntViewModelImpl(this, FlickrRepoImpl(RestClientImpl()), FlickrUtilImpl())
        viewModel.initData(this)
        setupView()
    }

    private fun setupView() {
        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(viewModel.getSearchTextListener())
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayout.VERTICAL, false)
        imageRV.layoutManager = gridLayoutManager
        imageRV.hasFixedSize()
        imageRV.adapter = viewModel.getAdapter()
        imageRV.addItemDecoration(viewModel.getItemDecorator())
    }

    override fun displayState(state: ViewState) {
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        progressBar.visibility = View.GONE
        imageRV.visibility = View.GONE
        when (state) {
            ViewState.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ViewState.ERROR -> {
                errorView.visibility = View.VISIBLE
            }
            ViewState.COMPLETE -> {
                imageRV.visibility = View.VISIBLE
            }
            ViewState.EMPTY -> {
                emptyView.visibility = View.VISIBLE
            }
        }
    }

    override fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
