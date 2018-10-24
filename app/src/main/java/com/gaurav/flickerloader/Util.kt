package com.gaurav.flickerloader

import com.gaurav.flickerloader.data.entity.Photo

fun getImageUrl(photo: Photo): String {
    return "http://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
}