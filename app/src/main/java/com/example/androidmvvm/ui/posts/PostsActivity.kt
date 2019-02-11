package com.example.androidmvvm.ui.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.androidmvvm.R
import com.example.androidmvvm.databinding.ActivityPostsBinding
import com.example.androidmvvm.ui.posts.adapters.PostListAdapter
import com.example.androidmvvm.utils.ViewModelFactory

class PostsActivity : AppCompatActivity(), View.OnClickListener {


  private lateinit var binding: ActivityPostsBinding
  private lateinit var viewModel: PostListViewModel
  private var errorSnackbar: Snackbar? = null
  private var adapter: PostListAdapter =
      PostListAdapter(ArrayList(), this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_posts)
    binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(
        PostListViewModel::class.java
    )
    binding.postList.adapter = adapter
    viewModel.errorMessage.observe(this, Observer { errorMessage ->
      if (errorMessage != null) showError(errorMessage) else hideError()
    })
    binding.viewModel = viewModel

    viewModel.postList.observe(this, Observer { list ->
      list?.let {
        adapter.setData(it)
      }
    })
  }

  override fun onClick(v: View?) {
    v?.let {
      val position = it.tag as Int
      val selectedPost = adapter.getItem(position)
      selectedPost?.let { post ->
        PostsNavigator.startPostDetailActivity(this, post)
      }
    }

  }

  private fun showError(@StringRes errorMessage: Int) {
    errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
    errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
    errorSnackbar?.show()
  }

  //
  private fun hideError() {
    errorSnackbar?.dismiss()
  }
}
