package com.example.androidmvvm.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.example.androidmvvm.ui.postDetail.PostDetailViewModel
import com.example.androidmvvm.ui.posts.PostListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
      return PostListViewModel(activity.application) as T
    } else if (modelClass.isAssignableFrom(PostDetailViewModel::class.java)) {
      return PostDetailViewModel(activity.application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")

  }
}