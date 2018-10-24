package com.gaurav.flickerloader.ui

import com.gaurav.flickerloader.BaseMvpPresenter
import com.gaurav.flickerloader.BaseMvpView

interface SearchImagePresenter<T : BaseMvpView> : BaseMvpPresenter<T> {

    fun searchImages(query: String?)
}