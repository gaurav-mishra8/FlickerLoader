package com.gaurav.flickerloader.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.inputmethod.InputMethodManager
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

    private val LOAD_MORE_THRESHOLD = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initFragments()
        initSearchView()
        initRecyclerView()
    }

    private fun initFragments() {
        var fragment = supportFragmentManager.findFragmentByTag(WorkerFragment.TAG)
        if (fragment == null) {
            fragment = WorkerFragment.getInstance()
            supportFragmentManager.beginTransaction().add(fragment, WorkerFragment.TAG).commitNow()
        }

        workerFragment = fragment as WorkerFragment
    }

    private fun initSearchView() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                workerFragment.searchImages(p0)
                hideKeyboard()
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
            hasFixedSize()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount

                    if (currentTotalCount <= lastItem + LOAD_MORE_THRESHOLD) {
                        recyclerView.post {
                            listAdapter.addLoadingViewFooter()
                        }
                        workerFragment.loadNextPage()
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun provideGridLayoutManager(): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(
            this@SearchImageActivity,
            resources.getInteger(R.integer.span_size),
            GridLayoutManager.VERTICAL,
            false
        )
        return gridLayoutManager
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }

    override fun onStartLoading() {
        progressBar.show()
        recyclerView.hide()
        resultStatus.hide()
    }

    override fun showResults(list: List<Photo>) {
        progressBar.hide()
        recyclerView.show()
        resultStatus.hide()

        listAdapter.removeLoadingViewFooter()
        listAdapter.updateResult(list)
    }

    override fun onError(errorMsg: String) {
        progressBar.hide()
        recyclerView.hide()
        resultStatus.show()

        resultStatus.text = errorMsg
        listAdapter.removeLoadingViewFooter()
    }
}
