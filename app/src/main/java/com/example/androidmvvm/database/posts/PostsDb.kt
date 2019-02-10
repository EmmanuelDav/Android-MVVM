package com.example.androidmvvm.database.posts

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.androidmvvm.model.Post

@Database(entities = [Post::class], version = 1)
abstract class PostsDb : RoomDatabase() {
  abstract fun postDao(): PostsDao
}