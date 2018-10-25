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

    companion object {
        const val VIEW_ITEM_TYPE = 0
        const val VIEW_FOOTER_TYPE = 1
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        when (viewType) {
            VIEW_ITEM_TYPE -> {
                onCreateItemViewHolder(viewGroup, viewType)
            }
            VIEW_FOOTER_TYPE -> {
                val footerView = LayoutInflater.from(context).inflate(R.layout.footer_view, viewGroup, false)
                FooterViewHolder(footerView)
            }
            else -> {
                throw IllegalArgumentException("Unknown view type")
            }
        }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 && showLoading) VIEW_FOOTER_TYPE else VIEW_ITEM_TYPE
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != VIEW_FOOTER_TYPE) onBindItemViewHolder(holder, position)
    }

    fun removeLoadingViewFooter() {
        if (showLoading && dataList.size > 0) {
            showLoading = false
            dataList.removeAt(dataList.size - 1)
            notifyItemRemoved(dataList.size)
        }
    }

    open fun addLoadingViewFooter(emptyDataObject: T) {
        if (dataList.size > 0 && !showLoading) {
            showLoading = true
            dataList.add(emptyDataObject)
            notifyItemChanged(dataList.size - 1)
        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract fun onCreateItemViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun addLoadingViewFooter()
}