package com.example.androidmvvm.database

import android.app.Application
import android.arch.persistence.room.Room
import com.example.androidmvvm.database.posts.PostsDao
import com.example.androidmvvm.database.posts.PostsDb

class DbHelper private constructor(application: Application) {

  private val postsDb: PostsDb = Room
      .databaseBuilder(application, PostsDb::class.java, "posts.db")
      .fallbackToDestructiveMigration()
      .build()

  val postsDao: PostsDao

  init {
    postsDao = postsDb.postDao()
  }

  companion object {

    private var dbHelper: DbHelper? = null

    fun getInstance(application: Application): DbHelper? {
      if (dbHelper == null) {
        synchronized(DbHelper::class.java) {
          dbHelper = DbHelper(application)
        }
      }
      return dbHelper
    }
  }


}
