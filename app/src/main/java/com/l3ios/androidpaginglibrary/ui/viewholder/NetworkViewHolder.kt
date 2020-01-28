package com.l3ios.androidpaginglibrary.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.l3ios.androidpaginglibrary.R
import com.l3ios.androidpaginglibrary.models.NetworkState
import com.l3ios.androidpaginglibrary.models.Status
import kotlinx.android.synthetic.main.page_list_loading.view.*

class NetworkViewHolder(view: View, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(view) {


    init {
        itemView.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        //  error message
        itemView.errorMessageTextView.visibility =
            if (networkState?.message != null) View.VISIBLE else View.GONE

        if (networkState?.message != null) {
            itemView.errorMessageTextView.text = networkState.message
        }

        //  loading and retry
        itemView.retryLoadingButton.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loadingProgressBar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.page_list_loading, parent, false)

            return NetworkViewHolder(view, retryCallback)
        }
    }

}