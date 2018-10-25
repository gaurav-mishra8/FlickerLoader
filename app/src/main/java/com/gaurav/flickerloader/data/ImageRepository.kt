package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.data.entity.PhotosResponse


interface ImageRepository {
    fun getImages(query: String, pageNum: Int = 0, callback: DataCallback)
}

interface DataCallback {
    fun onSuccess(response: PhotosResponse)
    fun onError(errorMsg: String?)
}