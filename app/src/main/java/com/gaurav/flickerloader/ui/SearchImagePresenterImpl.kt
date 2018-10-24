package com.gaurav.flickerloader.ui

import android.text.TextUtils
import com.gaurav.flickerloader.data.DataCallback
import com.gaurav.flickerloader.data.ImageRepository
import com.gaurav.flickerloader.data.entity.Photo

/**
 * Presenter class to interact with the repository and fetch results
 */
class SearchImagePresenterImpl<T : SearchImageView>(private val repository: ImageRepository) : SearchImagePresenter<T>,
    DataCallback<List<Photo>> {

    private var view: T? = null

    override fun onAttachView(view: T) {
        this.view = view
    }

    override fun searchImages(query: String?) {
        query?.let {
            view?.showLoading()
            repository.getImages(it, this)
        }
    }

    override fun onSuccess(response: List<Photo>) {
        view?.hideLoading()
        view?.setData(response)
    }

    override fun onError(errorMsg: String?) {
        view?.hideLoading()
        if (TextUtils.isEmpty(errorMsg)) {
            view?.showError("Something went wrong")
        } else {
            view?.showError(errorMsg!!)
        }
    }

    override fun onDetachView() {
        view = null
    }
}