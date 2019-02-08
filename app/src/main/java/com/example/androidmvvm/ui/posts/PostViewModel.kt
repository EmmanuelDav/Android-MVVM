package com.example.androidmvvm.ui.posts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.androidmvvm.model.Post

class PostViewModel : ViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post) {
        postTitle.value = post.title
        postBody.value = post.body
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody(): MutableLiveData<String> {
        return postBody
    }
}