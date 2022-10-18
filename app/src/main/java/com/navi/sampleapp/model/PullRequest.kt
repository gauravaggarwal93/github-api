package com.navi.sampleapp.model
import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("title")
    val title: String?,
    @SerializedName("created_at")
    val created_date: String?,
    @SerializedName("closed_at")
    val closed_date: String?,
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    val user: User
)
