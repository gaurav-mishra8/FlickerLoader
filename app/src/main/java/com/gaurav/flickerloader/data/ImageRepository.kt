package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.ui.Result

interface ImageRepository {
    fun getImages(query: String): Result
}