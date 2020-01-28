package com.l3ios.androidpaginglibrary.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.l3ios.androidpaginglibrary.api.WillingApi
import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.models.NetworkState
import io.reactivex.disposables.CompositeDisposable

class WillingDataSource(
    private val willingApi: WillingApi,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<String, FeedResult>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, FeedResult>
    ) {
        compositeDisposable.add(
            willingApi.exploreFeed(
                1.toString(),
                params.requestedLoadSize.toString(),
                "1"
            ).subscribe({ feedResult ->
                callback.onResult(
                    feedResult.data as MutableList<FeedResult>,
                    (feedResult.pagination?.nextPage?.minus(1)).toString(),
                    feedResult.pagination?.nextPage.toString()
                )
            }, { throwable ->
                throwable.printStackTrace()
            })
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, FeedResult>) {
        compositeDisposable.add(
            willingApi.exploreFeed(
                params.key,
                params.requestedLoadSize.toString(),
                "1"
            ).subscribe({ feedResult ->
                callback.onResult(
                    feedResult.data as MutableList<FeedResult>,
                    (feedResult.pagination?.nextPage?.plus(1).toString())
                )
            }, { throwable ->
                throwable.printStackTrace()

            })
        )
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, FeedResult>
    ) {
        // do nothing
    }

}