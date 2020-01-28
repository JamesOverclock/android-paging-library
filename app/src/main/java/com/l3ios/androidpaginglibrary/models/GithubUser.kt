package com.l3ios.androidpaginglibrary.models

import com.google.gson.annotations.SerializedName

class GithubUser (
    @SerializedName("login")
    var login: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("avatar_url")
    var avatar_url: String,
    @SerializedName("html_url")
    var html_url: String
)