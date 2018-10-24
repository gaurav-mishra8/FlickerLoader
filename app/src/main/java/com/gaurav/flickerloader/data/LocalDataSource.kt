package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.data.entity.Photo

class LocalDataSource : ImageRepository {

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(): LocalDataSource {
            return INSTANCE ?: synchronized(LocalDataSource::class.java) {
                INSTANCE ?: LocalDataSource().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun getImages(query: String, callback: DataCallback<List<Photo>>) {
        throw UnsupportedOperationException()
    }
}