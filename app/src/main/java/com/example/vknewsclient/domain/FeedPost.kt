package com.example.vknewsclient.domain

data class FeedPost(
    val id: String,
    val communityName: String,
    val publicationTime: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    var isFavourite: Boolean
)
