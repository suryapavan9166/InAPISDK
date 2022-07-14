package com.peoplelink.inapisdk.model

data class ProjectDataResponse(
    val `data`: Data,
    val status: Boolean
)

data class Data(
    val __v: Int,
    val _id: String,
    val appId: String,
    val appKey: String,
    val authToken: String,
    val createdAt: Long,
    val projectId: String,
    val userId: String
)