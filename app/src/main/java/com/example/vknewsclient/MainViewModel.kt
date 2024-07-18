package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.HomeScreenState.Posts
import com.example.vknewsclient.HomeScreenState.Comments
import com.example.vknewsclient.domain.PostComment

class MainViewModel : ViewModel() {

    private val postComments = mutableListOf<PostComment>().apply {
        repeat(20) {
            add(PostComment(id = it))
        }
    }

    private var feedPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val initialState = Posts(feedPosts)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun changeStatistics(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList()
        when (item.type) {
            StatisticType.VIEWS -> Unit
            StatisticType.SHARE -> newStatistics[1] =
                newStatistics[1].copy(count = newStatistics[1].count + 1)

            StatisticType.COMMENTS -> {
                newStatistics[2] = newStatistics[2].copy(count = newStatistics[2].count + 1)
            }

            StatisticType.LIKE -> newStatistics[3] =
                newStatistics[3].copy(count = newStatistics[3].count + 1)
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        feedPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = Posts(feedPosts)
    }


    fun deleteFeedPost(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is Posts) return
        feedPosts.remove(feedPost)
        _screenState.value = Posts(feedPosts)
    }

    fun changeState(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState is Posts) {
            val comments = Comments(feedPost, postComments)
            _screenState.value = comments
        } else {
            _screenState.value = Posts(feedPosts)
        }
    }
}