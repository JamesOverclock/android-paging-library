package com.l3ios.androidpaginglibrary.ui.itemkeyed


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager

import com.l3ios.androidpaginglibrary.R
import com.l3ios.androidpaginglibrary.models.GithubUser
import com.l3ios.androidpaginglibrary.models.NetworkState
import com.l3ios.androidpaginglibrary.api.GithubApi
import com.l3ios.androidpaginglibrary.ui.adapter.PageListAdapter
import kotlinx.android.synthetic.main.page_list_fragment.*

class ItemKeyedFragment : Fragment() {

    private val api by lazy {
        GithubApi.create()
    }

    companion object {
        fun newInstance() = ItemKeyedFragment()
    }

    private lateinit var githubAdapter: PageListAdapter
    private lateinit var viewModel: ItemKeyedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ItemKeyedViewModel::class.java)
        initLayoutManager()
    }

    private fun initLayoutManager() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        githubAdapter = PageListAdapter {
            viewModel.retry()
        }

        page_list_rv.layoutManager = linearLayoutManager
        page_list_rv.adapter = githubAdapter

        viewModel.userList.observe(this, Observer<PagedList<GithubUser>> {
            githubAdapter.submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer<NetworkState> {
            githubAdapter.setNetworkState(it)
        })
    }


}
