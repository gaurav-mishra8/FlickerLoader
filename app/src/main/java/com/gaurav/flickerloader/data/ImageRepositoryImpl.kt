package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.data.entity.Photo

class ImageRepositoryImpl(
    private val remoteDataSource: ImageRepository,
    private val localDataSource: ImageRepository
) : ImageRepository {

    companion object {

        @Volatile
        private var INSTANCE: ImageRepository? = null

        fun getInstance(
            remoteDataSource: ImageRepository,
            localDataSource: ImageRepository
        ): ImageRepository {

            return INSTANCE ?: synchronized(ImageRepositoryImpl::class.java) {
                INSTANCE ?: ImageRepositoryImpl(
                    remoteDataSource = remoteDataSource,
                    localDataSource = localDataSource
                ).also {
                    INSTANCE = it
                }
            }

        }
    }

    override fun getImages(query: String, callback: DataCallback<List<Photo>>) {
        return remoteDataSource.getImages(query, callback)
    }
}