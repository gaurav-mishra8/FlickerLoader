package com.gaurav.flickerloader.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.gaurav.flickerloader.Injection
import com.gaurav.flickerloader.data.entity.Photo

/**
 * Worker fragment to interact with repository and get image search results
 */
class WorkerFragment : Fragment(), SearchImageView {

    internal interface ImageResultsCallbacks {
        fun onStartLoading()
        fun onError(errorMsg: String)
        fun showResults(list: List<Photo>)
    }

    companion object {
        const val TAG = "WorkerFragment"
        fun getInstance(): WorkerFragment = WorkerFragment()
    }

    private var imageResultsCallbacks: ImageResultsCallbacks? = null
    private lateinit var searchImagePresenter: SearchImagePresenterImpl<SearchImageView>

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ImageResultsCallbacks) {
            imageResultsCallbacks = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        initPresenter()
    }

    private fun initPresenter() {
        searchImagePresenter = SearchImagePresenterImpl(Injection.provideImageRepository(activity!!.applicationContext))
        searchImagePresenter.onAttachView(this)
    }

    fun searchImages(query: String?) {
        searchImagePresenter.searchImages(query)
    }

    override fun setData(imageResults: List<Photo>) {
        imageResultsCallbacks?.showResults(imageResults)
    }

    override fun showLoading() {
        imageResultsCallbacks?.onStartLoading()
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
        imageResultsCallbacks?.onError(errorMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        imageResultsCallbacks = null
        searchImagePresenter.onDetachView()
    }

    fun loadNextPage() {
        searchImagePresenter.loadNextPage()
    }
}
