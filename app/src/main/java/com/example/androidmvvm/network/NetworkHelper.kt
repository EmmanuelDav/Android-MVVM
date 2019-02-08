package com.example.androidmvvm.network

import android.app.Application
import com.example.androidmvvm.R
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkHelper private constructor(private val application: Application) {
    val postsApi: PostsApi

    init {


        //        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(application.cacheDir, cacheSize.toLong())

        val client = OkHttpClient.Builder().cache(cache)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            //                .addInterceptor(new HeaderInterceptor(application.getApplicationContext()))
            //                .addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor())
            //                .addInterceptor(logging)
            .retryOnConnectionFailure(true)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()

        postsApi = retrofit.create(PostsApi::class.java)
    }

    companion object {

        private var networkHelper: NetworkHelper? = null


        fun getInstance(application: Application): NetworkHelper? {
            if (networkHelper == null) {
                synchronized(NetworkHelper::class.java) {
                    networkHelper = NetworkHelper(application)
                }
            }
            return networkHelper
        }
    }
}
