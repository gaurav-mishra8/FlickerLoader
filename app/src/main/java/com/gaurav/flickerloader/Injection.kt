package com.gaurav.flickerloader

import android.content.Context
import com.gaurav.flickerloader.data.ImageRepository
import com.gaurav.flickerloader.data.ImageRepositoryImpl
import com.gaurav.flickerloader.data.LocalDataSource
import com.gaurav.flickerloader.data.RemoteDataSource
import com.gaurav.flickerloader.data.apiservice.ApiService

object Injection {

    fun provideApiService(): ApiService {
        return ApiService()
    }

    fun provideImageRepository(context: Context): ImageRepository {
        return ImageRepositoryImpl.getInstance(
            remoteDataSource = RemoteDataSource.getInstance(),
            localDataSource = LocalDataSource.getInstance()
        )
    }
}