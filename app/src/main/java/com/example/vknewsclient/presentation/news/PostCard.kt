package com.example.vknewsclient.presentation.news

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vknewsclient.R
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.ui.theme.DarkRed


@Composable
fun PostCard(
    feedPost: FeedPost,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            PostHeader(
                communityName = feedPost.communityName,
                publicationTime = feedPost.publicationTime,
                avatarResId = feedPost.communityImageUrl
            )
            Spacer(modifier = Modifier.height(8.dp))
            PostBody(
                contentText = feedPost.contentText,
                contentImageItem = feedPost.contentImageUrl
            )
            Spacer(modifier = Modifier.height(8.dp))
            PostFooter(
                isFavourite = feedPost.isFavourite,
                statistic = feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onCommentClickListener = onCommentClickListener,
                onShareClickListener = onShareClickListener,
                onViewsClickListener = onViewsClickListener
            )
        }
    }
}

@Composable
fun PostHeader(
    communityName: String,
    publicationTime: String,
    avatarResId: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            model = avatarResId,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = communityName, color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = publicationTime, color = MaterialTheme.colorScheme.onSecondary)
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun PostBody(
    contentText: String,
    contentImageItem: String?
) {
    Text(
        text = contentText, color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    AsyncImage(
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
        model = contentImageItem,
        contentDescription = null
    )
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}


@Composable
fun PostFooter(
    isFavourite: Boolean,
    statistic: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewItem = statistic.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCount(viewItem.count),
                onItemClickListener = {
                    onViewsClickListener(viewItem)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val shareItem = statistic.getItemByType(StatisticType.SHARE)
            IconWithText(iconResId = R.drawable.ic_share,
                text = formatStatisticCount(shareItem.count),
                onItemClickListener = {
                    onShareClickListener(shareItem)
                })
            val commentItem = statistic.getItemByType(StatisticType.COMMENTS)
            IconWithText(iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentItem.count),
                onItemClickListener = {
                    onCommentClickListener(commentItem)
                })
            val likeItem = statistic.getItemByType(StatisticType.LIKE)
            IconWithText(iconResId = if (isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likeItem.count),
                tint = if (isFavourite) DarkRed else MaterialTheme.colorScheme.onPrimary,
                onItemClickListener = {
                    onLikeClickListener(likeItem)
                })
        }

    }
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
        Text(text = text, modifier = Modifier.padding(start = 4.dp))
    }
}

@SuppressLint("DefaultLocale")
private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}