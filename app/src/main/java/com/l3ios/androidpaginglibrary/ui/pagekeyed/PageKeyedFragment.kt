package com.l3ios.androidpaginglibrary.ui.pagekeyed


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
import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.api.GithubApi
import com.l3ios.androidpaginglibrary.ui.adapter.PageKeyedAdapter
import kotlinx.android.synthetic.main.page_list_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class PageKeyedFragment : Fragment() {

    private val api by lazy {
        GithubApi.create()
    }

    companion object {
        fun newInstance() = PageKeyedFragment()
    }

    private lateinit var willingAdapter: PageKeyedAdapter
    private lateinit var viewModel: PageKeyedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PageKeyedViewModel::class.java)
        initLayoutManager()
    }

    private fun initLayoutManager() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        willingAdapter = PageKeyedAdapter {
        }

        page_list_rv.layoutManager = linearLayoutManager
        page_list_rv.adapter = willingAdapter

        viewModel.userList?.observe(this, Observer<PagedList<FeedResult>> {
            willingAdapter.submitList(it)
        })
    }


}
