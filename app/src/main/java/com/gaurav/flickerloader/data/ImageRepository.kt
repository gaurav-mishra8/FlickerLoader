package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.data.entity.PhotosResponse


interface ImageRepository {
    fun getImages(query: String, pageNum: Int = 0, callback: DataCallback<PhotosResponse>)
}

interface DataCallback<R> {
    fun onSuccess(response: R)
    fun onError(errorMsg: String?)
}