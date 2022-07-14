package com.peoplelink.inapisdk.model

data class SessionRequest(
    val projectId: String,
    val sessionName: String,
    val token: String,
    val entryTime: Int,
    val meetingDetails: MeetingDetails
)

data class MeetingDetails(
    val duration: Int,
    val hostUid: String,
    val meetingId: String,
    val meetingName: String,
    val participantLimit: Int
)