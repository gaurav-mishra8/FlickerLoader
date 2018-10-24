package com.gaurav.flickerloader.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.ProgressBar
import android.widget.TextView
import com.gaurav.flickerloader.R
import com.gaurav.flickerloader.data.entity.Photo
import com.gaurav.flickerloader.hide
import com.gaurav.flickerloader.show

class SearchImageActivity : AppCompatActivity(), WorkerFragment.ImageResultsCallbacks {

    private lateinit var workerFragment: WorkerFragment
    private val listAdapter by lazy { ImageListAdapter(this) }
    private val searchView by lazy { findViewById<SearchView>(R.id.searchView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val resultStatus by lazy { findViewById<TextView>(R.id.tvNoResults) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initFragments()
        initSearchView()
        initRecyclerView()
    }

    private fun initFragments() {
        val fragment = supportFragmentManager.findFragmentByTag(WorkerFragment.TAG)
        if (fragment == null) {
            workerFragment = WorkerFragment.getInstance()
            supportFragmentManager.beginTransaction().add(workerFragment, WorkerFragment.TAG).commitNow()
        }
    }

    private fun initSearchView() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                workerFragment.searchImages(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = true
        })
    }

    private fun initRecyclerView() {
        recyclerView?.apply {
            val gridLayoutManager = provideGridLayoutManager()
            layoutManager = gridLayoutManager
            adapter = listAdapter
        }
    }

    private fun provideGridLayoutManager(): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(this@SearchImageActivity, 3, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(pos: Int): Int {
                if (pos > listAdapter.itemCount) {
                    return 1
                }
                return 3
            }
        }
        return gridLayoutManager
    }

    override fun onStartLoading() {
        progressBar.show()
        recyclerView.hide()
        resultStatus.hide()
    }

    override fun showResults(list: List<Photo>) {
        progressBar.hide()
        recyclerView.show()
        listAdapter.updateResult(list)
    }

    override fun onError(errorMsg: String) {
        progressBar.hide()
        recyclerView.hide()
        resultStatus.show()
        resultStatus.text = errorMsg
    }
}
