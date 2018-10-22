package com.gaurav.flickerloader.data.apiservice

import android.net.Uri
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ApiService {

    companion object {
        const val BASE_URL =
            "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1"
        const val READ_TIMEOUT = 3000
        const val CONNECTION_TIMEOUT = 5000
    }

    fun fetchSearchResults(query: String): String? {
        val url = getUrl(query)

        var stream: InputStream? = null
        var connection: HttpURLConnection? = null
        var result: String? = null

        try {
            connection = url.openConnection() as HttpURLConnection?
            connection?.run {
                readTimeout = READ_TIMEOUT
                connectTimeout = CONNECTION_TIMEOUT
                requestMethod = "GET"
                doInput = true
                connect()
            }

            val responseCode = connection?.responseCode
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: $responseCode")
            }
            stream = connection?.inputStream
            if (stream != null) {
                result = readStream(stream, 500)
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            stream?.close()
            connection?.disconnect()
        }

        return result
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    @Throws(IOException::class, UnsupportedEncodingException::class)
    fun readStream(stream: InputStream, maxReadSize: Int): String? {
        val reader: Reader? = InputStreamReader(stream, "UTF-8")
        val rawBuffer = CharArray(maxReadSize)
        val buffer = StringBuffer()
        var readSize: Int = reader?.read(rawBuffer) ?: -1
        var maxReadBytes = maxReadSize
        while (readSize != -1 && maxReadBytes > 0) {
            if (readSize > maxReadBytes) {
                readSize = maxReadBytes
            }
            buffer.append(rawBuffer, 0, readSize)
            maxReadBytes -= readSize
            readSize = reader?.read(rawBuffer) ?: -1
        }
        return buffer.toString()
    }

    /**
     * Build uri with given query param
     * @param query The query string to append
     * @return The URL object with appended query param
     */
    private fun getUrl(query: String): URL {
        val baseUri = Uri.parse(BASE_URL)
        val buildUri = baseUri.buildUpon().appendQueryParameter("text", query).build()
        return URL(buildUri.toString())
    }
}