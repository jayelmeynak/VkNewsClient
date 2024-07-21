package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.ui.theme.NewsFeedScreenState
import com.example.vknewsclient.ui.theme.NewsFeedScreenState.Posts

class NewsFeedViewModel : ViewModel() {

    private var feedPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(id = it)
            )
        }
    }
    private val initialState = Posts(feedPosts)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

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
//        feedPosts.remove(feedPost)
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = Posts(modifiedList)
    }
}