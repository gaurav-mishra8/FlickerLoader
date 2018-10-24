package com.gaurav.flickerloader

interface BaseMvpPresenter<T : BaseMvpView> {

    fun onAttachView(view: T)

    fun onDetachView()

}