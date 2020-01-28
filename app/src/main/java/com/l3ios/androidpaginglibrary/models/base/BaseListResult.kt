package com.l3ios.androidpaginglibrary.models.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseListResult <T> {
    @SerializedName("response_code")
    @Expose
    var response_code: Int? = null
    @SerializedName("response_msg")
    @Expose
    var response_msg: String? = null
    @SerializedName("result")
    @Expose
    var data: List<T>? = null
    @SerializedName("pagination")
    @Expose
    var pagination: Pagination? = null
}