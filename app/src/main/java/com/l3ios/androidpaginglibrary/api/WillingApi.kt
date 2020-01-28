package com.l3ios.androidpaginglibrary.api

import com.l3ios.androidpaginglibrary.models.FeedResult
import com.l3ios.androidpaginglibrary.models.base.BaseListResult
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WillingApi {

    @GET("explore_feed")
    fun exploreFeed(@Query("page") page: String, @Query("perpage") perPage: String, @Query("user_id") userId: String): Observable<BaseListResult<FeedResult>>

    companion object {
        private const val BASE_URL = "http://api.willing.in.th/main/"

        fun create(): WillingApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WillingApi::class.java)
        }
    }
}