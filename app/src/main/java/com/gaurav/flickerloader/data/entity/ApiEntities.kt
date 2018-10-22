package com.gaurav.flickerloader.data.entity

data class PhotosResponse(
    val page: Int? = 0,
    val pages: Int? = 0,
    val perPage: Int? = 0,
    val total: Int? = 0,
    val photoList: List<Photo>? = null
)

data class Photo(
    val id: String? = null,
    val secret: String? = null,
    val server: String? = null,
    val farm: Int? = 0,
    val title: String? = null
)