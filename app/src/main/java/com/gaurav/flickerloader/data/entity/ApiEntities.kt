package com.gaurav.flickerloader.data.entity

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    val photos: Photos? = null,
    val state: String? = null
)

data class Photos(
    val page: Int = 0,
    val pages: Int = 0,
    val perPage: Int = 0,
    val total: Int = 0,
    @SerializedName("photo") val photoList: List<Photo>? = null
)

data class Photo(
    val id: String? = null,
    val secret: String? = null,
    val server: String? = null,
    val farm: Int? = 0,
    val title: String? = null
)