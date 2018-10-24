package com.gaurav.flickerloader

interface BaseMvpView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String)

}