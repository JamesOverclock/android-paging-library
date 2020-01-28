package com.l3ios.androidpaginglibrary.ui.itemkeyed

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.l3ios.androidpaginglibrary.common.pageSize
import com.l3ios.androidpaginglibrary.models.GithubUser
import com.l3ios.androidpaginglibrary.models.NetworkState
import com.l3ios.androidpaginglibrary.api.GithubApi
import com.l3ios.androidpaginglibrary.ui.repository.GithubUserDataSource
import com.l3ios.androidpaginglibrary.ui.repository.GithubUserDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class ItemKeyedViewModel : ViewModel() {

    var userList: LiveData<PagedList<GithubUser>>

    private val compositeDisposable = CompositeDisposable()

    private val sourceFactory: GithubUserDataSourceFactory

    init {
        sourceFactory = GithubUserDataSourceFactory(compositeDisposable, GithubApi.create())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 5)
            .setEnablePlaceholders(false)
            .build()

        userList = LivePagedListBuilder<Long, GithubUser>(sourceFactory, config).build()
    }

    fun retry() {
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<GithubUserDataSource, NetworkState>(sourceFactory.usersDataSourceLiveData) {
            it.networkState
        }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}