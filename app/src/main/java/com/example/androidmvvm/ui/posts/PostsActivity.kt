package com.example.androidmvvm.ui.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.androidmvvm.R
import com.example.androidmvvm.databinding.ActivityPostsBinding
import com.example.androidmvvm.ui.postDetail.PostDetailActivity
import com.example.androidmvvm.utils.INTENT_EXTRA_POST
import com.example.androidmvvm.utils.ViewModelFactory

class PostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsBinding
    private lateinit var viewModel: PostListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Injector.appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts)
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(PostListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        viewModel.selectedPost.observe(this, Observer { selectedPost ->
            selectedPost?.let {
                val intent = Intent(this, PostDetailActivity::class.java)
                intent.putExtra(INTENT_EXTRA_POST, selectedPost)
                startActivity(intent)
            }
        })
        binding.viewModel = viewModel


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
