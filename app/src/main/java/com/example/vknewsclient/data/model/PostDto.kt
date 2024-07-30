package com.example.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("post_id") val id: String,
    @SerializedName("source_id") val communityId: Int,
    @SerializedName("is_favourite") val isFavourite: Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
)