package com.l3ios.androidpaginglibrary.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.l3ios.androidpaginglibrary.models.GithubUser
import com.l3ios.androidpaginglibrary.api.GithubApi
import io.reactivex.disposables.CompositeDisposable


class GithubUserDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val githubService: GithubApi
) : DataSource.Factory<Long, GithubUser>() {

    val usersDataSourceLiveData = MutableLiveData<GithubUserDataSource>()

    override fun create(): DataSource<Long, GithubUser> {
        val usersDataSource = GithubUserDataSource(githubService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}