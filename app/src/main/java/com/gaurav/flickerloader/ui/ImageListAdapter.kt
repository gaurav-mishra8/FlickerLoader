package com.gaurav.flickerloader.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gaurav.flickerloader.R
import com.gaurav.flickerloader.data.entity.Photo
import com.gaurav.flickerloader.getImageUrl
import com.gaurav.flickerloader.widget.PaginatedAdapter


class ImageListAdapter(context: Context) : PaginatedAdapter<Photo>(context) {

    override fun onCreateItemViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false)
        return ImageItemViewHolder(itemView)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageItemViewHolder) {
            holder.bind(dataList[position])
        }
    }

    fun updateResult(list: List<Photo>) {
        val currentSize = dataList.size
        dataList.addAll(list)
        notifyItemRangeInserted(currentSize, list.size)
    }

    class ImageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val requestOptions = RequestOptions()

        init {
            requestOptions.placeholder(ColorDrawable(Color.parseColor("#ebebeb")))
        }

        fun bind(photo: Photo) {
            Glide.with(itemView.context).load(getImageUrl(photo))
                .apply(requestOptions)
                .into(imageView)
        }
    }
}