package com.gaurav.flickerloader.data

import com.gaurav.flickerloader.Injection
import com.gaurav.flickerloader.data.api.FlickerApi
import com.gaurav.flickerloader.data.entity.PhotosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val flickerApi: FlickerApi) : ImageRepository {

    companion object {

        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource {
            return INSTANCE ?: synchronized(RemoteDataSource::class.java) {
                INSTANCE ?: return RemoteDataSource(Injection.provideFlickerApi()).also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun getImages(query: String, pageNum: Int, callback: DataCallback<PhotosResponse>) {
        val call = flickerApi.getImageResults(query, pageNum)
        call.enqueue(object : Callback<PhotosResponse> {
            override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<PhotosResponse>, response: Response<PhotosResponse>) {
                if (response.isSuccessful) {
                    val photoResponse = response.body()
                    callback.onSuccess(photoResponse!!)
                } else {
                    callback.onError(response.message())
                }
            }
        })
    }
}