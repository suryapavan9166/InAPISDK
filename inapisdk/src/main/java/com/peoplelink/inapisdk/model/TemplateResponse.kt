package com.peoplelink.inapisdk.model

data class TemplateResponse(
    val `data`: TemplateData,
    val status: Boolean
)

data class TemplateData(
    val subDomain: String,
    val projectId: String,
)