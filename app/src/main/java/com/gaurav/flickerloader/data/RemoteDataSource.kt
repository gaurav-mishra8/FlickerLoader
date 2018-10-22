package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.Injection
import com.gaurav.flickerloader.data.apiservice.ApiService
import com.gaurav.flickerloader.ui.Result
import com.gaurav.flickerloader.ui.ResultError
import com.gaurav.flickerloader.ui.ResultSuccess

class RemoteDataSource(private val apiService: ApiService) : ImageRepository {

    companion object {

        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource {
            return INSTANCE ?: synchronized(RemoteDataSource::class.java) {
                INSTANCE ?: return RemoteDataSource(Injection.provideApiService()).also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun getImages(query: String): Result {
        val response = apiService.fetchSearchResults(query)
        if (response != null) {
            return ResultSuccess(response)
        } else {
            return ResultError(Exception("Something went wrong"))
        }
    }
}