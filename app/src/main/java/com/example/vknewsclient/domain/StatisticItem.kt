package com.example.vknewsclient.domain

data class StatisticItem(
    val type: StatisticType,
    val count: Int
)

enum class StatisticType{
    VIEWS, SHARE, COMMENTS, LIKE
}