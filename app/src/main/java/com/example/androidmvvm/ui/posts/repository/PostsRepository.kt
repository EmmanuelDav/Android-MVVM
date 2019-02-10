package com.example.androidmvvm.ui.posts.repository

import android.app.Application
import com.example.androidmvvm.database.DbHelper
import com.example.androidmvvm.database.posts.PostsDao
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.network.NetworkHelper
import com.example.androidmvvm.network.PostsApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PostsRepository(
    application: Application
) {

  private var postsApi: PostsApi = NetworkHelper.getInstance(application)!!.postsApi
  private var postsDao: PostsDao = DbHelper.getInstance(application)!!.postsDao


  fun getPostsFromDb(): Observable<List<Post>> {
    return postsDao.getPosts().filter { it.isNotEmpty() }
        .toObservable()
        .doOnNext {
          Timber.d("Dispatching ${it.size} users from DB...")
        }
  }

  fun getPostsFromApi(): Observable<List<Post>> {
    return postsApi.getPosts()
        .doOnNext {
          Timber.d("Dispatching ${it.size} users from API...")
          storePostInDb(it)
        }
  }

  fun storePostInDb(posts: List<Post>) {
    Observable.fromCallable { postsDao.insertAll(posts) }
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe {
          Timber.d("Inserted ${posts.size} users from API in DB...")
        }
  }

}