package com.l3ios.androidpaginglibrary.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.l3ios.androidpaginglibrary.api.WillingApi
import com.l3ios.androidpaginglibrary.models.FeedResult
import io.reactivex.disposables.CompositeDisposable

class WillingDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val willingApi: WillingApi
) : DataSource.Factory<String, FeedResult>() {

    val usersDataSourceLiveData = MutableLiveData<WillingDataSource>()

    override fun create(): DataSource<String, FeedResult> {
        val willingDataSource = WillingDataSource(willingApi, compositeDisposable)
        usersDataSourceLiveData.postValue(willingDataSource)
        return willingDataSource
    }
}