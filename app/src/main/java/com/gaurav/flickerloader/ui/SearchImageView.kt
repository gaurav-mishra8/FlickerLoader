package com.gaurav.flickerloader.ui

import com.gaurav.flickerloader.BaseMvpView
import com.gaurav.flickerloader.data.entity.Photo

interface SearchImageView : BaseMvpView {

    fun setData(imageResults: List<Photo>)
}