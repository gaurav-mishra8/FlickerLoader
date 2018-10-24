package com.gaurav.flickerloader.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gaurav.flickerloader.R

abstract class PaginatedAdapter<T>(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val dataList = mutableListOf<T>()
    private var showLoading = false

    val VIEW_ITEM_TYPE = 0
    val VIEW_FOOTER_TYPE = 1

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder =

        when (getItemViewType(position)) {
            VIEW_ITEM_TYPE -> {
                onCreateItemViewHolder(viewGroup, position)
            }
            VIEW_FOOTER_TYPE -> {
                val footerView = LayoutInflater.from(context).inflate(R.layout.footer_view, viewGroup, false)
                FooterViewHolder(footerView)
            }
            else -> {
                throw IllegalArgumentException("Unnknown view type")
            }
        }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int =
        if (position == dataList.size - 1 && showLoading) VIEW_FOOTER_TYPE else VIEW_ITEM_TYPE


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FooterViewHolder -> {
            }
            else -> {
                onBindItemViewHolder(holder, position)
            }
        }
    }

    fun removeLoadingViewFooter() {
        if (showLoading && dataList.size > 0) {
            showLoading = false
            dataList.removeAt(dataList.size - 1)
            notifyItemRemoved(dataList.size)
        }
    }

    open fun addLoadingViewFooter(emptyDataObject: T) {
        if (dataList.size > 0) {
            showLoading = true
            dataList.add(emptyDataObject)
            notifyItemInserted(dataList.size - 1)
        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract fun onCreateItemViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder

    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun addLoadingViewFooter()
}