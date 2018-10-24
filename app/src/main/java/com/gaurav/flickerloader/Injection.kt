package com.gaurav.flickerloader

import android.content.Context
import com.gaurav.flickerloader.data.ImageRepository
import com.gaurav.flickerloader.data.ImageRepositoryImpl
import com.gaurav.flickerloader.data.LocalDataSource
import com.gaurav.flickerloader.data.RemoteDataSource
import com.gaurav.flickerloader.data.api.FlickerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    private var flickerApi: FlickerApi? = null

    private const val BASE_URL = "https://api.flickr.com/"

    fun provideImageRepository(context: Context): ImageRepository {
        return ImageRepositoryImpl.getInstance(
            remoteDataSource = RemoteDataSource.getInstance(),
            localDataSource = LocalDataSource.getInstance()
        )
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private fun provideRetrofitClient(): Retrofit {
        val okHttpClient = provideOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideFlickerApi(): FlickerApi {
        if (flickerApi != null) return flickerApi!! else {
            val retrofit = provideRetrofitClient()
            return retrofit.create(FlickerApi::class.java).also {
                flickerApi = it
            }
        }
    }

}