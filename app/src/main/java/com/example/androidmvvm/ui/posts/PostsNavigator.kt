package com.example.androidmvvm.ui.posts

import android.content.Intent
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.ui.postDetail.PostDetailActivity
import com.example.androidmvvm.utils.INTENT_EXTRA_POST

class PostsNavigator {

    companion object {
        fun startPostDetailActivity(activity: PostsActivity, selectedPost: Post) {
            val intent = Intent(activity, PostDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_POST, selectedPost)
            activity.startActivity(intent)
        }
    }

}
