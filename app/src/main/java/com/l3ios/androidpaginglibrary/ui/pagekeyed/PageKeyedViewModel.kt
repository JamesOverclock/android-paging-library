package com.l3ios.androidpaginglibrary.ui.pagekeyed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.l3ios.androidpaginglibrary.api.WillingApi
import com.l3ios.androidpaginglibrary.common.pageSize
import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.ui.repository.WillingDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class PageKeyedViewModel : ViewModel() {

    var userList: LiveData<PagedList<FeedResult>>? = null

    private val compositeDisposable = CompositeDisposable()

    val sourceFactory: WillingDataSourceFactory

    init {
        sourceFactory = WillingDataSourceFactory(compositeDisposable, WillingApi.create())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

        userList = LivePagedListBuilder<String, FeedResult>(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}