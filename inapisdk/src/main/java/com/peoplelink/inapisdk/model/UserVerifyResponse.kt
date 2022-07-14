package com.peoplelink.inapisdk.model

data class UserVerifyResponse(
    val `data`: UserVerifyData,
    val status: Boolean
)

data class UserVerifyData(
    val appId: String,
    val appKey: String
)