package com.example.androidmvvm.network

import com.example.androidmvvm.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
}