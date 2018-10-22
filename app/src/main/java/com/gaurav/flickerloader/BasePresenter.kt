package com.gaurav.flickerloader

interface BasePresenter<T : BaseView> {

    fun onAttach(view: T)

    fun onDetach()

}