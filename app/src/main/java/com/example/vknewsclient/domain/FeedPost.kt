package com.example.vknewsclient.domain

import com.example.vknewsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "dev/null",
    val publicationTime: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "LoremIpsum",
    val contentImageItem: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 996),
        StatisticItem(StatisticType.SHARE, 65),
        StatisticItem(StatisticType.COMMENTS, 23),
        StatisticItem(StatisticType.LIKE, 346)
    )
)
