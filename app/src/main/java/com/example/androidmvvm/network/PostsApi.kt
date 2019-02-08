package com.example.androidmvvm.network

import com.example.androidmvvm.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    @GET("/posts/{id}")
    fun  getPostDetail(@Path("id") id :Int):Observable<Post>
}