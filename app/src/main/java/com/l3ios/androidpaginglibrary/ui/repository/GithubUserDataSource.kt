package com.l3ios.androidpaginglibrary.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.l3ios.androidpaginglibrary.models.GithubUser
import com.l3ios.androidpaginglibrary.models.NetworkState
import com.l3ios.androidpaginglibrary.api.GithubApi
import io.reactivex.disposables.CompositeDisposable

class GithubUserDataSource(
    private val githubService: GithubApi,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Long, GithubUser>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<GithubUser>
    ) {
        compositeDisposable.add(
            githubService.getUsers(
                1,
                params.requestedLoadSize
            ).subscribe({ users ->
                callback.onResult(users)
            }, { throwable ->
                throwable.printStackTrace()
            })
        )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<GithubUser>) {
        //  get the users from the api after id
        networkState.postValue(NetworkState.LOADED)
        compositeDisposable.add(
            githubService.getUsers(
                params.key,
                params.requestedLoadSize
            ).subscribe({ users ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(users)
            }, { throwable ->
                networkState.postValue(NetworkState.error(throwable.message))
                throwable.printStackTrace()
            })
        )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<GithubUser>) {
        //  ignored, since we only ever append to our initial load
    }

    override fun getKey(item: GithubUser): Long {
        return item.id
    }

}