package com.gaurav.flickerloader

import com.gaurav.flickerloader.data.entity.Photo
import com.gaurav.flickerloader.data.entity.Photos
import com.gaurav.flickerloader.data.entity.PhotosResponse

fun emptyPhotoResponse() = PhotosResponse()

fun dummyPhotoList(): List<Photo> {
    val photoList = mutableListOf<Photo>()
    photoList.add(Photo(id = "1", secret = "secret1", server = "11", farm = 1, title = "title1"))
    photoList.add(Photo(id = "2", secret = "secret2", server = "12", farm = 2, title = "title2"))
    photoList.add(Photo(id = "3", secret = "secret3", server = "13", farm = 3, title = "title3"))
    photoList.add(Photo(id = "4", secret = "secret4", server = "14", farm = 4, title = "title4"))
    photoList.add(Photo(id = "5", secret = "secret5", server = "15", farm = 5, title = "title5"))
    photoList.add(Photo(id = "6", secret = "secret6", server = "16", farm = 6, title = "title6"))
    photoList.add(Photo(id = "7", secret = "secret7", server = "17", farm = 7, title = "title7"))

    return photoList
}

fun listPhotoResponse(): PhotosResponse {
    val photos = Photos(photoList = dummyPhotoList())
    return PhotosResponse(photos = photos)
}

