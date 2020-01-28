package com.l3ios.androidpaginglibrary.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.l3ios.androidpaginglibrary.R
import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.models.NetworkState
import com.l3ios.androidpaginglibrary.ui.viewholder.NetworkViewHolder
import com.l3ios.androidpaginglibrary.ui.viewholder.UserViewHolder
import java.lang.IllegalArgumentException

class PageKeyedAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<FeedResult, RecyclerView.ViewHolder>(DiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.page_list_item -> UserViewHolder.create(parent)
            R.layout.page_list_loading -> NetworkViewHolder.create(parent, retryCallback)
            else -> throw  IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.page_list_item -> (holder as UserViewHolder).bindTo(getItem(position))
            R.layout.page_list_loading -> (holder as NetworkViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.page_list_loading
        } else {
            R.layout.page_list_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList?.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<FeedResult>() {

            override fun areItemsTheSame(oldItem: FeedResult, newItem: FeedResult): Boolean {
                return oldItem.contentId == newItem.contentId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FeedResult, newItem: FeedResult): Boolean {
                return oldItem == newItem
            }
        }
    }

}