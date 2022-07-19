package com.peoplelink.inapisdk.model

data class GetTokenResponse(
    val `data`: TokenData,
    val status: Boolean
)

data class TokenData(
    val projectId: String,
    val token: String
)