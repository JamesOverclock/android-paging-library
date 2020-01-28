package com.l3ios.androidpaginglibrary.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.l3ios.androidpaginglibrary.R
import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.models.GithubUser
import kotlinx.android.synthetic.main.page_list_item.view.*

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(user: GithubUser?) {
        itemView.adapter_page_list_tv.text = user?.login
        Glide.with(itemView.context)
            .load(user?.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.ic_launcher)
            .into(itemView.adapter_page_list_iv)

    }

    fun bindTo(user: FeedResult?) {
        itemView.adapter_page_list_tv.text = user?.name
        Glide.with(itemView.context)
            .load(user?.thumbnailUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.ic_launcher)
            .into(itemView.adapter_page_list_iv)

    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.page_list_item, parent, false)
            return UserViewHolder(view)
        }
    }

}