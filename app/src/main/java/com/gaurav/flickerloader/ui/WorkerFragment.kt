package com.gaurav.flickerloader.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.gaurav.flickerloader.BaseView
import com.gaurav.flickerloader.Injection

class WorkerFragment : Fragment(), BaseView {

    private lateinit var mainPresenter: MainActivityPresenter<BaseView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        initPresenter()
    }

    private fun initPresenter() {
        mainPresenter = MainActivityPresenter(Injection.provideImageRepository(activity!!.applicationContext))
        mainPresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDetach()
    }
}
