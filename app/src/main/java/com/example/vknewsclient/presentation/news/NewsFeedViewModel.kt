package com.example.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vknewsclient.data.mapper.NewsFeedMapper
import com.example.vknewsclient.data.network.ApiFactory
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.presentation.news.NewsFeedScreenState.Posts
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private var feedPosts = mutableListOf<FeedPost>()

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val mapper = NewsFeedMapper()

    init {
        loadNews()
    }

    fun loadNews(){
        viewModelScope.launch{
            val  storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ApiFactory.apiService.loadNews(token.accessToken)
            Log.d("MyLog", response.toString())
            val feedPosts = mapper.mapResponseToPosts(response)
            _screenState.value = Posts(feedPosts)
        }
    }

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

            StatisticType.LIKE -> {
                if(!feedPost.isFavourite){
                    newStatistics[3] = newStatistics[3].copy(count = newStatistics[3].count + 1)
                    feedPost.isFavourite = !feedPost.isFavourite
                }
                else{
                    newStatistics[3] = newStatistics[3].copy(count = newStatistics[3].count - 1)
                    feedPost.isFavourite = !feedPost.isFavourite
                }
            }
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
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = Posts(modifiedList)
    }
}