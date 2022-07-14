package com.peoplelink.inapisdk.model

data class SessionResponse(
    val `data`: SessionData,
    val status: Boolean
)

data class SessionData(
    val sessionId: String
)