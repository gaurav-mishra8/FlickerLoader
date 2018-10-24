package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.data.entity.Photo


interface ImageRepository {
    fun getImages(query: String, callback: DataCallback<List<Photo>>)
}

interface DataCallback<R> {
    fun onSuccess(response: R)
    fun onError(errorMsg: String?)
}