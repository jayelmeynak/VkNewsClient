package com.example.vknewsclient.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment
import com.example.vknewsclient.presentation.comments.CommentsScreenState.Initial
import com.example.vknewsclient.presentation.comments.CommentsScreenState.Comments

class CommentsViewModel(feedPost: FeedPost): ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost){
        val comments = mutableListOf<PostComment>().apply {
            repeat(20){
                add(PostComment(id = it))
            }
        }
        _screenState.value = Comments(feedPost, comments)
    }
}