package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType

class MainViewModel: ViewModel() {

    private val _feedPost =  MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun changeStatistics(item: StatisticItem){
        val oldStatistics = feedPost.value?.statistics ?: throw IllegalStateException()
        val newStatistics = oldStatistics.toMutableList()
        when(item.type){
            StatisticType.VIEWS -> Unit
            StatisticType.SHARE -> newStatistics[1] = newStatistics[1].copy(count = newStatistics[1].count + 1)
            StatisticType.COMMENTS -> newStatistics[2] = newStatistics[2].copy(count = newStatistics[2].count + 1)
            StatisticType.LIKE -> newStatistics[3] = newStatistics[3].copy(count = newStatistics[3].count + 1)
        }
        _feedPost.value = _feedPost.value?.copy(statistics = newStatistics)

    }
}