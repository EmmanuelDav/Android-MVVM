package com.example.androidmvvm.ui.postDetail.repository

import android.app.Application
import com.example.androidmvvm.database.DbHelper
import com.example.androidmvvm.database.posts.PostsDao
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.network.NetworkHelper
import com.example.androidmvvm.network.PostsApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PostDetailRepository(application: Application) {

  private var postsApi: PostsApi = NetworkHelper.getInstance(application)!!.postsApi
  private var postsDao: PostsDao = DbHelper.getInstance(application)!!.postsDao

  fun getPostDetailFromApi(id: Int): Observable<Post> {
    return postsApi.getPostDetail(id).doOnNext {
      Timber.d("Post fetched from API...")
      updatePost(it)
    }
  }

  fun getPostDetailFromDb(id: Int): Observable<Post> {
    return postsDao.getPostById(id).toObservable()
        .doOnNext {
          Timber.d("Post ${it.id} fetched from db")
        }
  }


  fun updatePost(post: Post) {
    Observable.fromCallable { postsDao.updatePost(post) }
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe {
          Timber.d("Updated post with id ${post.id} from API in DB...")
        }
  }

}