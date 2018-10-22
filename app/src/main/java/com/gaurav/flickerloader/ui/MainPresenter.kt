package com.gaurav.flickerloader.ui

import com.gaurav.flickerloader.BasePresenter
import com.gaurav.flickerloader.BaseView

interface MainPresenter<T : BaseView> : BasePresenter<T> {

    fun searchImages(query: String?)
}