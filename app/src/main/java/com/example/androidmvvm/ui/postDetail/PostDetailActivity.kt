package com.example.androidmvvm.ui.postDetail

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.androidmvvm.R
import com.example.androidmvvm.constants.INTENT_EXTRA_POST
import com.example.androidmvvm.databinding.ActivityPostDetailBinding
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.utils.ViewModelFactory

class PostDetailActivity : AppCompatActivity() {

  private lateinit var binding: ActivityPostDetailBinding
  private lateinit var viewModel: PostDetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(
        PostDetailViewModel::class.java)
    binding.viewModel = viewModel
    val post = intent.getParcelableExtra<Post>(INTENT_EXTRA_POST)
    viewModel.post.value = post
    viewModel.loadPostDetail()
  }
}