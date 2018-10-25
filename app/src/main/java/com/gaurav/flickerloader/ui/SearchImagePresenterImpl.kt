package com.gaurav.flickerloader.ui

import com.gaurav.flickerloader.data.DataCallback
import com.gaurav.flickerloader.data.ImageRepository
import com.gaurav.flickerloader.data.entity.PhotosResponse

/**
 * Presenter class to interact with the repository and fetch results
 */
class SearchImagePresenterImpl<T : SearchImageView>(private val repository: ImageRepository) : SearchImagePresenter<T>,
    DataCallback {

    private var view: T? = null
    private var queryTerm: String? = null
    private var totalPages: Int = 0
    private var currentPage: Int = 0
    private var isLoading = false

    override fun onAttachView(view: T) {
        this.view = view
    }

    override fun searchImages(query: String?) {
        query?.let {
            queryTerm = it
            view?.showLoading()
            isLoading = true
            repository.getImages(it, 0, this)
        }
    }

    override fun loadNextPage() {

        if (!isLoading && hasMoreData()) {
            queryTerm?.let {
                isLoading = true
                repository.getImages(queryTerm!!, ++currentPage, this)
            }
        }
    }

    override fun onSuccess(response: PhotosResponse) {
        view?.hideLoading()
        isLoading = false

        totalPages = response.photos?.pages ?: 0
        currentPage = response.photos?.page ?: 0

        val photoList = response.photos?.photoList
        photoList?.let {
            view?.setData(photoList)
        } ?: view?.showError("Something went wrong")
    }

    override fun onError(errorMsg: String?) {
        view?.hideLoading()
        isLoading = false

        if (errorMsg == null || errorMsg.isEmpty()) {
            view?.showError("Something went wrong")
        } else {
            view?.showError(errorMsg!!)
        }
    }

    private fun hasMoreData(): Boolean = currentPage < totalPages

    override fun onDetachView() {
        view = null
    }
}