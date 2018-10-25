package com.gaurav.flickerloader.ui

import com.gaurav.flickerloader.capture
import com.gaurav.flickerloader.data.DataCallback
import com.gaurav.flickerloader.data.ImageRepository
import com.gaurav.flickerloader.dummyPhotoList
import com.gaurav.flickerloader.emptyPhotoResponse
import com.gaurav.flickerloader.listPhotoResponse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.*

class SearchImagePresenterImplTest {

    @Mock
    lateinit var view: SearchImageView

    @Mock
    lateinit var repository: ImageRepository

    @Captor
    lateinit var mDataCallbackCaptor: ArgumentCaptor<DataCallback>

    lateinit var searchImagePresenter: SearchImagePresenter<SearchImageView>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mDataCallbackCaptor = ArgumentCaptor.forClass(DataCallback::class.java)

        searchImagePresenter = SearchImagePresenterImpl(repository)
        searchImagePresenter.onAttachView(view)
    }

    @Test
    fun checkViewIsAttached() {
        assertNotNull(view)
    }

    @Test
    fun checkShowLoadingCalled() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(view).showLoading()
    }

    @Test
    fun checkHideLoadingCalledOnSuccess() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(repository).getImages(Mockito.anyString(), Mockito.anyInt(), capture(mDataCallbackCaptor))

        mDataCallbackCaptor.value.onSuccess(emptyPhotoResponse())
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun checkHideLoadingCalledOnError() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(repository).getImages(Mockito.anyString(), Mockito.anyInt(), capture(mDataCallbackCaptor))

        mDataCallbackCaptor.value.onError("Error")
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun checkShowErrorCalledForEmptyList() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(repository).getImages(Mockito.anyString(), Mockito.anyInt(), capture(mDataCallbackCaptor))

        mDataCallbackCaptor.value.onSuccess(emptyPhotoResponse())
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showError("Something went wrong")
    }

    @Test
    fun checkSetDataCalledForPhotoList() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(repository).getImages(Mockito.anyString(), Mockito.anyInt(), capture(mDataCallbackCaptor))

        mDataCallbackCaptor.value.onSuccess(listPhotoResponse())
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).setData(dummyPhotoList())
    }

    @Test
    fun checkShowErrorCalledForNetworkError() {
        searchImagePresenter.searchImages("query")

        Mockito.verify(repository).getImages(Mockito.anyString(), Mockito.anyInt(), capture(mDataCallbackCaptor))

        mDataCallbackCaptor.value.onError("error")
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showError(Mockito.anyString())
    }
}