package com.gaurav.flickerloader.data.api

import com.gaurav.flickerloader.data.entity.PhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerApi {

    @GET("services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1")
    fun getImageResults(@Query("text") query: String, @Query("page") page: Int = 0): Call<PhotosResponse>
}