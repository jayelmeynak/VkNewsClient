package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(id = it)
            )
        }
    }


    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun changeStatistics(feedPost: FeedPost, item: StatisticItem) {
        val oldPosts = _feedPosts.value?.toMutableList() ?: mutableListOf()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList()
        when (item.type) {
            StatisticType.VIEWS -> Unit
            StatisticType.SHARE -> newStatistics[1] =
                newStatistics[1].copy(count = newStatistics[1].count + 1)

            StatisticType.COMMENTS -> newStatistics[2] =
                newStatistics[2].copy(count = newStatistics[2].count + 1)

            StatisticType.LIKE -> newStatistics[3] =
                newStatistics[3].copy(count = newStatistics[3].count + 1)
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
    }


    fun deleteFeedPost(feedPost: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _feedPosts.value = modifiedList
    }
}