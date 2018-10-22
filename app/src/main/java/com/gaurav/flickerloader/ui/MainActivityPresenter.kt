package com.gaurav.flickerloader.ui

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.gaurav.flickerloader.BaseView
import com.gaurav.flickerloader.data.ImageRepository

class MainActivityPresenter<T : BaseView>(private val repository: ImageRepository) : MainPresenter<T> {

    private var view: BaseView? = null
    private var fetchImageResults: FetchImageResultsTask? = null

    override fun onAttach(view: T) {
        this.view = view
    }

    override fun searchImages(query: String?) {
        query?.let {
            startDownload(it)
        }
    }

    private fun startDownload(query: String) {
        cancelDownload()
        fetchImageResults = FetchImageResultsTask()
        fetchImageResults!!.execute(query)
    }

    private fun cancelDownload() {
        fetchImageResults?.cancel(true)
        fetchImageResults = null
    }

    @SuppressLint("StaticFieldLeak")
    inner class FetchImageResultsTask : AsyncTask<String, Void, Result>() {

        override fun onPostExecute(result: Result?) {
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String): Result {
            return repository.getImages(params[0])
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }
    }

    override fun onDetach() {
        view = null
    }
}


sealed class Result
class ResultSuccess(val response: String) : Result()
class ResultError(val exception: Exception) : Result()