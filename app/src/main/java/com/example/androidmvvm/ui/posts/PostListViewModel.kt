package com.example.androidmvvm.ui.posts

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.androidmvvm.R
import com.example.androidmvvm.base.BaseViewModel
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.repository.PostsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostListViewModel(application: Application) : BaseViewModel(application) {

    private var postsRepository: PostsRepository = PostsRepository(application)

    private lateinit var networkSubscription: Disposable
    private lateinit var dbSubscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val buttonVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val selectedPost: MutableLiveData<Post> = MutableLiveData()


    val errorClickListener = View.OnClickListener {
        loadPosts()
        buttonVisibility.value = View.GONE
    }

    private val listItemClickListener = View.OnClickListener {
        val position = it.tag as Int
        selectedPost.value = postListAdapter.getItem(position)
    }
    val postListAdapter: PostListAdapter = PostListAdapter(ArrayList(), listItemClickListener)


    private fun loadPosts() {

        networkSubscription = postsRepository.getPostsFromApi().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doOnTerminate { hideLoading() }
            .subscribe(
                { postList -> onPostsSuccess(postList) },
                { onPostsError() }
            )


    }

    private fun hideLoading() {
        loadingVisibility.value = View.GONE
    }

    private fun showLoading() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onPostsError() {
        dbSubscription = postsRepository.getPostsFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {}
            .doOnTerminate {}
            .subscribe(
                { postList ->
                    onPostsFetchedFromDb(postList)
                }, {
                    onErrorFetchingFromDb()
                }
            )
    }

    private fun onPostsSuccess(postList: List<Post>) {
        //postsRepository.storePostInDb(postList)
        postListAdapter.setData(postList)

    }

    private fun onPostsFetchedFromDb(postList: List<Post>) {
        postListAdapter.setData(postList)
    }

    private fun onErrorFetchingFromDb() {
        errorMessage.value = R.string.post_error
    }

    override fun onCleared() {
        super.onCleared()
        if (::networkSubscription.isInitialized)
            networkSubscription.dispose()
        if (::dbSubscription.isInitialized)
            dbSubscription.dispose()
    }
}