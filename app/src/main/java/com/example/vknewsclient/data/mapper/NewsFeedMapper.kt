package com.example.vknewsclient.data.mapper

import com.example.vknewsclient.data.model.NewsFeedResponseDto
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {


    fun mapResponseToPosts(response: NewsFeedResponseDto): List<FeedPost>{
        val listFeedPost = mutableListOf<FeedPost>()
        val posts = response.newsFeedContent.posts
        val groups = response.newsFeedContent.groups
        for(post in posts){
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationTime = mapTimestampToDate(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.SHARE, count = post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.LIKE, count = post.likes.count)

                ),
                isFavourite = post.isFavourite
            )
            listFeedPost.add(feedPost)
        }
        return listFeedPost
    }
}

private fun mapTimestampToDate(timestamp: Long): String{
    val date = Date(timestamp)
    return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
}